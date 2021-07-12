package space.ffisherr.labgnss.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.ffisherr.labgnss.jpa.repository.GnssFileRepository;
import space.ffisherr.labgnss.model.GnssFileModel;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GnssFileServiceImpl implements GnssFileService {

    private final GnssFileRepository repository;

    @Override
    public List<GnssFileModel> readAll() {
        return null;
    }

}
