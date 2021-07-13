package space.ffisherr.labgnss.component;

import org.springframework.stereotype.Component;
import space.ffisherr.labgnss.jpa.entity.GnssFileEntity;
import space.ffisherr.labgnss.model.GnssFileModel;

@Component
public class GnssFileConverter {

    public GnssFileModel convertFromEntity(GnssFileEntity entity) {
        final GnssFileModel model = new GnssFileModel();
        model.setId(entity.getId());
        model.setIsValid(entity.getIsValid());
        model.setName(entity.getName());
        model.setYear(entity.getYear());
        model.setMonth(entity.getMonth());
        model.setDay(entity.getDay());
        return model;
    }

}
