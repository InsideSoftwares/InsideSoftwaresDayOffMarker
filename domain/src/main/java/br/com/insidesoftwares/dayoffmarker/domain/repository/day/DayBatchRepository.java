package br.com.insidesoftwares.dayoffmarker.domain.repository.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DayBatchRepository extends JpaRepository<DayBatch, Long> {

	boolean existsByDate(final LocalDate date);
}
