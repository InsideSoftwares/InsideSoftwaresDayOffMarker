package br.com.insidesoftwares.dayoffmarker.repository.holiday;

import br.com.insidesoftwares.dayoffmarker.entity.holiday.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {

	@EntityGraph(value = "holiday-full")
	@Override
	Page<Holiday> findAll(
			Specification<Holiday> holidaySpecification,
			Pageable pageable
	);

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
