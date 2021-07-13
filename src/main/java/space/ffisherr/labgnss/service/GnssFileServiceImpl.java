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
    private final GnssFileRepository repository;
    private final GnssFileConverter converter;

    @Override
    public Page<GnssFileModel> readAll(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertFromEntity);
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
        String readFile = "";
        try {
            readFile = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            log.error("Error while reading file", e);
        }
        final Boolean isFileValid = validateFile(readFile, FilenameUtils.getExtension(file.getName()));
        if (!isFileValid) {
            log.warn("File {} is invalid", file);
            return false;
        }
        return true;
    }

    private Boolean validateFile(String text, String extension) {
        boolean valid = false;
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
        }
        return valid;
    }

}
