package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

	Optional<Day> findByDateAndCountry(LocalDate date, Country country);

	@Query("SELECT MAX(d.date) FROM Day d WHERE d.date >= :date")
	Optional<LocalDate> findMaxDateByDate(LocalDate date);

	@Query("SELECT MIN(d.date) FROM Day d WHERE d.date >= :date")
	Optional<LocalDate> findMinDateByDate(LocalDate date);

	@Query("SELECT count(d) > 0 FROM Day d WHERE d.date = :date ")
	boolean ownsDays(LocalDate date);
}
