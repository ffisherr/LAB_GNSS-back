package space.ffisherr.labgnss.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import space.ffisherr.labgnss.component.GnssFileConverter;
import space.ffisherr.labgnss.jpa.repository.GnssFileRepository;
import space.ffisherr.labgnss.model.GnssFileModel;



@Slf4j
@Service
@RequiredArgsConstructor
public class GnssFileServiceImpl implements GnssFileService {

    private final GnssFileRepository repository;
    private final GnssFileConverter converter;

    @Override
    public Page<GnssFileModel> readAll(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertFromEntity);
    }

}
