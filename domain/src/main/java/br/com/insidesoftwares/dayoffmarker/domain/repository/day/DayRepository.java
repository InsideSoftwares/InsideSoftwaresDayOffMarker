package br.com.insidesoftwares.dayoffmarker.domain.repository.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DayRepository extends JpaRepository<Day, UUID>, JpaSpecificationExecutor<Day>, QuerydslPredicateExecutor<FixedHoliday> {

    @EntityGraph(value = "day-full")
    Optional<Day> findDayById(final UUID id);

    @EntityGraph(value = "day-full")
    @Override
    Page<Day> findAll(Specification<Day> daySpecification, Pageable pageable);

    @EntityGraph(value = "day-full")
    @Override
    Optional<Day> findOne(Specification<Day> daySpecification);

    @Query("SELECT d.date FROM Day d WHERE d.id = :dayID")
    LocalDate findDateByID(final UUID dayID);

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
    List<Day> findDaysByTagId(final UUID tagID);

    @Query("""
            SELECT d
            FROM Day d
            INNER JOIN d.tags t
            WHERE LOWER(t.code) = LOWER(:tagCode)
            """)
    @EntityGraph(value = "day-full")
    List<Day> findDaysByTagCode(final String tagCode);
    @Override
    boolean exists(Specification<Day> daySpecification);

    boolean existsByDate(final LocalDate date);

    @Override
    @EntityGraph(value = "day-tags")
    List<Day> findAll(Specification<Day> daySpecification);
}
