package br.com.insidesoftwares.dayoffmarker.domain.repository.city;

import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CityHolidayRepository extends JpaRepository<CityHoliday, UUID> {

}
