package space.ffisherr.labgnss.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.ffisherr.labgnss.model.GnssFileModel;
import space.ffisherr.labgnss.service.GnssFileService;

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

}
