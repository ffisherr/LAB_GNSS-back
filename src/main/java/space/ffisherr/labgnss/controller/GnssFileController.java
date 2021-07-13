package space.ffisherr.labgnss.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.ffisherr.labgnss.model.GnssFileModel;
import space.ffisherr.labgnss.service.GnssFileService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files/")
@RequiredArgsConstructor
public class GnssFileController {

    private final GnssFileService service;

    @GetMapping
    private Page<GnssFileModel> readAll(Pageable pageable) {
        log.info("Reading all files with pageable params: {}", pageable);
        return service.readAll(pageable);
    }

    @GetMapping("/years/")
    private List<GnssFileModel> readAllYears() {
        log.info("Reading all years");
        return service.readAllYears();
    }

    @GetMapping("/{year}/months/")
    private List<GnssFileModel> readAllMonths(@PathVariable String year) {
        log.info("Reading all months by {} year", year);
        return service.readAllMonthByYear(year);
    }

    @GetMapping("/{year}/{month}/days/")
    private List<GnssFileModel> readAllYears(@PathVariable String year, @PathVariable String month) {
        log.info("Reading all days by {} year {} month", year, month);
        return service.readAllMonthByYearAndMonth(year, month);
    }

    @GetMapping("/{year}/{month}/{day}/")
    private List<GnssFileModel> readDay(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
        log.info("Reading day by {} year {} month {} day", year, month, day);
        return service.readAllByParticularDay(year, month, day);
    }


}
