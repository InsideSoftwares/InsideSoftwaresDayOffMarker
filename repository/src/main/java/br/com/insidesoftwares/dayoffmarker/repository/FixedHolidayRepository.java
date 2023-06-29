package br.com.insidesoftwares.dayoffmarker.repository;

import br.com.insidesoftwares.dayoffmarker.entity.holiday.FixedHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedHolidayRepository extends JpaRepository<FixedHoliday, Long> {

    @Query("""
            SELECT count(f)>0
            FROM FixedHoliday f
            WHERE f.day = :day AND
            f.month = :month
            """)
    boolean existsByDayAndMonth(Integer day, Integer month);

    @Query("""
            SELECT count(f)>0
            FROM FixedHoliday f
            WHERE f.day = :day AND
            f.month = :month AND
            f.id != :fixedHolidayId
            """)
    boolean existsByDayAndMonthAndNotId(Integer day, Integer month, Long fixedHolidayId);

}
