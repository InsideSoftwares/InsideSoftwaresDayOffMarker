package br.com.insidesoftwares.dayoffmarker.repository.city;

import br.com.insidesoftwares.dayoffmarker.entity.city.CityHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityHolidayRepository extends JpaRepository<CityHoliday, Long> {

}
