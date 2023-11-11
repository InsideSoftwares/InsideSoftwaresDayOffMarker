package br.com.insidesoftwares.dayoffmarker.domain.repository.holiday;

import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FixedHolidayRepository extends JpaRepository<FixedHoliday, UUID> {

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
    boolean existsByDayAndMonthAndNotId(Integer day, Integer month, UUID fixedHolidayId);

    List<FixedHoliday> findAllByIsEnable(boolean isEnable);
}
