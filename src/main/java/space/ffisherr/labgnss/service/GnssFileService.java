package space.ffisherr.labgnss.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.ffisherr.labgnss.model.GnssFileModel;

public interface GnssFileService {

    Page<GnssFileModel> readAll(Pageable pageable);

}
