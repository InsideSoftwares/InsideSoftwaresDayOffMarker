package br.com.insidesoftwares.dayoffmarker.domain.repository.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long>, JpaSpecificationExecutor<Day> {

	Optional<Day> findByDate(final LocalDate date);

    @EntityGraph(value = "day-full")
    Optional<Day> findDayById(final Long id);

	@EntityGraph(value = "day-full")
	@Override
	Page<Day> findAll(Specification<Day> daySpecification, Pageable pageable);

	@EntityGraph(value = "day-full")
	@Override
	Optional<Day> findOne(Specification<Day> daySpecification);

	@Query("SELECT MAX(d.date) FROM Day d WHERE d.date >= :date")
	Optional<LocalDate> findMaxDateByDate(final LocalDate date);

	@Query("SELECT MIN(d.date) FROM Day d WHERE d.date >= :date")
	Optional<LocalDate> findMinDateByDate(final LocalDate date);

	@Query("SELECT d.date FROM Day d WHERE d.id = :dayID")
	LocalDate findDateByID(final Long dayID);

	@Query("SELECT count(d) > 0 FROM Day d WHERE d.date > :date ")
	boolean ownsDays(final LocalDate date);

	@Query("""
			SELECT count(d) > 0
			FROM Day d
			JOIN d.tags t
			WHERE d.id = :dayID AND
			t.id = :tagID
			""")
	boolean existsByDateAndTag(final Long dayID, final Long tagID);

    @EntityGraph(value = "day-full")
	@Query("""
			SELECT d FROM Day d
			WHERE d.date BETWEEN :dateStartSearch AND :dateFinalSearch
			AND d.isHoliday = :isHoliday
			AND d.isWeekend = :isWeekend
			""")
	List<Day> findAllByDateSearchAndHolidayAndWeekend(
		final LocalDate dateStartSearch,
		final LocalDate dateFinalSearch,
		final boolean isHoliday,
		final boolean isWeekend
	);

	@Query("""
			SELECT d FROM Day d
			WHERE d.date BETWEEN :dateStartSearch AND :dateFinalSearch
			""")
	@EntityGraph(value = "day-full")
	List<Day> findAllByDateSearch(
		final LocalDate dateStartSearch,
		final LocalDate dateFinalSearch
	);

	@Query("""
			SELECT count(d) > 0
			FROM Day d
			WHERE d.date > CURRENT_DATE
			""")
	boolean existsDay(final int limit);

	@Query("""
			SELECT d
			FROM Day d
			INNER JOIN d.tags t
			WHERE t.id = :tagID
			""")
	@EntityGraph(value = "day-full")
	List<Day> findDaysByTagId(final Long tagID);

	@Query("""
			SELECT d
			FROM Day d
			INNER JOIN d.tags t
			WHERE LOWER(t.code) = LOWER(:tagCode)
			""")
	@EntityGraph(value = "day-full")
	List<Day> findDaysByTagCode(final String tagCode);

	@Query("""
		SELECT count(d) > 0
		 FROM Day d
		 WHERE d.date = :dateSearch
			AND d.isWeekend = :isWeekend
		""")
	boolean isWorkingDayByDateAndIsWeekend(
		final LocalDate dateSearch,
		final boolean isWeekend
	);

	@Query("""
		SELECT count(d) > 0
		 FROM Day d
		 WHERE d.date = :dateSearch
			AND d.isHoliday = :isHoliday
			AND d.isWeekend = :isWeekend
		""")
	boolean isWorkingDayByDateAndIsHolidayAndIsWeekend(
		final LocalDate dateSearch,
		final boolean isHoliday,
		final boolean isWeekend
	);

	@Query("""
		SELECT count(d) > 0
		 FROM Day d
		 LEFT JOIN Holiday h ON h.day = d
		 WHERE ( h is null OR h.fixedHolidayID <> :fixedHolidayID)
		 AND DAY(d.date) = :day
		 AND MONTH(d.date) = :month
		 AND YEAR(d.date) >= :year
		""")
	boolean isDaysWithoutHolidaysByByDayAndMonthAndYearAndFixedHolidayIDOrNotHoliday(
		final Integer day,
		final Integer month,
		final Integer year,
		final Long fixedHolidayID
	);

	@Override
	boolean exists(Specification<Day> daySpecification);

    boolean existsByDate(final LocalDate date);

	@Override
	@EntityGraph(value = "day-tags")
	List<Day> findAll(Specification<Day> daySpecification);
}
