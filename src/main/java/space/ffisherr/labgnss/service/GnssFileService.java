package space.ffisherr.labgnss.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.ffisherr.labgnss.model.GnssFileModel;

import java.io.File;
import java.util.List;

public interface GnssFileService {

    Page<GnssFileModel> readAll(Pageable pageable);

    List<GnssFileModel> readAllYears();

    List<GnssFileModel> readAllMonthByYear(String year);

    List<GnssFileModel> readAllMonthByYearAndMonth(String year, String month);

    List<GnssFileModel> readAllByParticularDay(String year, String month, String day);

    List<String> readAllPath();

    Boolean loadFile(File file);

}
