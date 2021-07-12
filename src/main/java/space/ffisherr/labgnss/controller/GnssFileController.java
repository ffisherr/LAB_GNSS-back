package space.ffisherr.labgnss.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.ffisherr.labgnss.model.GnssFileModel;

import java.util.List;

@Slf4j
@RestController
public class GnssFileController {

    @GetMapping
    private List<GnssFileModel> readAll() {

    }

}
