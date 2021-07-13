package space.ffisherr.labgnss.model;

import lombok.Data;

@Data
public class GnssFileModel {

    private Long id;
    private String name;
    private Boolean isValid;
    private String year;
    private String month;
    private String day;

}
