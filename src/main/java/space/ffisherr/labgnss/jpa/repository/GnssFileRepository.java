package space.ffisherr.labgnss.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.ffisherr.labgnss.jpa.entity.GnssFileEntity;

import java.util.List;

public interface GnssFileRepository extends JpaRepository<GnssFileEntity, Long> {

    @Query("select distinct g.year from GnssFileEntity g where g.isValid = true")
    List<String> readAllYears();

    @Query("select distinct g.month from GnssFileEntity g where g.year = :year and g.isValid = true")
    List<String> readAllMonthByYear(@Param("year") String year);

    @Query("select distinct g.day from GnssFileEntity g " +
            "where g.year = :year and g.month = :month and g.isValid = true")
    List<String> readAllDaysByMonthAndYear(@Param("year") String year,
                                           @Param("month") String month);

    @Query("select g from GnssFileEntity g " +
            "where g.year = :year and g.month = :month and g.day = :day and g.isValid = true")
    List<GnssFileEntity> readAllByDay(@Param("year") String year,
                                      @Param("month") String month,
                                      @Param("day") String day);

}
