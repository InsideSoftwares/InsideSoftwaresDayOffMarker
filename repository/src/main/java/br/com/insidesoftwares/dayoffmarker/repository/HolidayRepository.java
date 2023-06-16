package br.com.insidesoftwares.dayoffmarker.repository;

import br.com.insidesoftwares.dayoffmarker.entity.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	@Query("""
            SELECT h FROM Holiday h
            WHERE h.day.date BETWEEN :startDate AND :endDate
            """)
	@EntityGraph(value = "holiday-full")
	Page<Holiday> findAllByStartDateAndEndDate(
			LocalDate startDate,
			LocalDate endDate,
			Pageable pageable
	);
	@EntityGraph(value = "holiday-full")
	@Override
	Page<Holiday> findAll(Pageable pageable);

	@Query("""
            SELECT h
            FROM Holiday h
            WHERE h.day.id = :dayID
            """)
	Optional<Holiday> findByDayID(Long dayID);

    @Query("""
            SELECT count(h)>0
            FROM Holiday h
            WHERE h.day.id = :dayID
            """)
    boolean existsByDayID(Long dayID);

    @Query("""
            SELECT count(h)>0
            FROM Holiday h
            WHERE h.day.id = :dayID AND
            h.id != :holidayId
            """)
    boolean existsByDayIDAndNotId(Long dayID, Long holidayId);

}
