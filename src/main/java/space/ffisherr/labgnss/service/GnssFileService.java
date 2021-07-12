package space.ffisherr.labgnss.service;

import space.ffisherr.labgnss.model.GnssFileModel;

import java.util.List;

public interface GnssFileService {

    List<GnssFileModel> readAll();

}
