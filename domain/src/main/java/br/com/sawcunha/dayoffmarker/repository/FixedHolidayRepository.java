package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixedHolidayRepository extends JpaRepository<FixedHoliday, Long> {

	Page<FixedHoliday> findAllByCountry(Country country, Pageable pageable);
	List<FixedHoliday> findAllByCountry(Country country);

    @Query("""
            SELECT count(f)>0
            FROM FixedHoliday f
            WHERE f.day = :day AND
            f.month = :month AND
            f.country.id = :countryId
            """)
    boolean existsByDayAndMonthAndCountryId(Integer day, Integer month, Long countryId);

    @Query("""
            SELECT count(f)>0
            FROM FixedHoliday f
            WHERE f.day = :day AND
            f.month = :month AND
            f.country.id = :countryId AND
            f.id != :fixedHolidayId
            """)
    boolean existsByDayAndMonthAndCountryIdAndNotId(Integer day, Integer month, Long countryId, Long fixedHolidayId);

}
