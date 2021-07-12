package space.ffisherr.labgnss.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import space.ffisherr.labgnss.jpa.entity.GnssFileEntity;

public interface GnssFileRepository extends CrudRepository<GnssFileEntity, Long> {
}
