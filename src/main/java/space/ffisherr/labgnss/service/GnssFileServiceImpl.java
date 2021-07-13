package space.ffisherr.labgnss.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import space.ffisherr.labgnss.component.GnssFileConverter;
import space.ffisherr.labgnss.jpa.entity.GnssFileEntity;
import space.ffisherr.labgnss.jpa.repository.GnssFileRepository;
import space.ffisherr.labgnss.model.GnssFileModel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class GnssFileServiceImpl implements GnssFileService {

    private static final List<Integer> AVAILABLE_PAR_NUMBERS = Arrays.asList(25, 27, 33);
    private static final Long MAP_FILE_SIZE = 32L;
    private final GnssFileRepository repository;
    private final GnssFileConverter converter;

    @Override
    public Page<GnssFileModel> readAll(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertFromEntity);
    }

    @Override
    public List<GnssFileModel> readAllYears() {
        return repository.readAllYears()
                .stream().map(v -> {
                    final GnssFileModel model = new GnssFileModel();
                    model.setYear(v);
                    return model;
                }).collect(Collectors.toList());
    }

    @Override
    public List<GnssFileModel> readAllMonthByYear(String year) {
        return repository.readAllMonthByYear(year)
                .stream().map(v -> {
                    final GnssFileModel model = new GnssFileModel();
                    model.setYear(year);
                    model.setMonth(v);
                    return model;
                }).collect(Collectors.toList());
    }

    @Override
    public List<GnssFileModel> readAllMonthByYearAndMonth(String year, String month) {
        return repository.readAllDaysByMonthAndYear(year, month)
                .stream().map(v -> {
                    final GnssFileModel model = new GnssFileModel();
                    model.setYear(year);
                    model.setMonth(month);
                    model.setDay(v);
                    return model;
                }).collect(Collectors.toList());
    }

    @Override
    public List<GnssFileModel> readAllByParticularDay(String year, String month, String day) {
        return repository.readAllByDay(year, month, day)
                .stream().map(converter::convertFromEntity).collect(Collectors.toList());
    }

    @Override
    public List<String> readAllPath() {
        return repository.findAll().stream().map(GnssFileEntity::getPath).collect(Collectors.toList());
    }

    @Override
    public Boolean loadFile(File file) {
        if (file == null || !file.exists()) {
            log.error("Service cannot load file");
            return false;
        }
        log.debug("Service loads file {}", file.getAbsolutePath());
        final Boolean isFileValid = validateFile(file);
        final String[] date = file.getName().split("-");
        final GnssFileEntity entity = new GnssFileEntity();
        entity.setIsValid(true);
        entity.setName(file.getName());
        entity.setPath(file.getAbsolutePath());
        entity.setYear(date[0]);
        entity.setMonth(date[1]);
        entity.setDay(date[2]);
        repository.save(entity);
        if (!isFileValid) {
            log.warn("File {} is invalid", file);
            return false;
        }
        return true;
    }

    private Boolean validateFile(File file) {
        final String extension = FilenameUtils.getExtension(file.getName());
        boolean valid = false;
        String text = "";
        try {
            text = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            log.error("Error while reading file", e);
        }
        if (text == null || text.isEmpty()) {
            log.warn("File is empty");
            valid = false;
        } else if ("par".equals(extension)) {
            final int paragraphNumber = StringUtils.countOccurrencesOf(text, "\n");
            if (AVAILABLE_PAR_NUMBERS.contains(paragraphNumber)) {
                valid =  true;
            } else {
                log.warn("Broken PAR file");
            }
        } else if ("map".equals(extension)) {
            final long size = FileUtils.sizeOf(file);
            if (size % MAP_FILE_SIZE == 0) {
                valid = true;
            } else {
                log.warn("Broken MAP file");
            }
        }
        return valid;
    }

}
