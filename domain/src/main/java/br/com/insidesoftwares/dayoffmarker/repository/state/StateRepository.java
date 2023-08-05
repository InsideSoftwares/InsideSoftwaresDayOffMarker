package br.com.insidesoftwares.dayoffmarker.repository.state;

import br.com.insidesoftwares.dayoffmarker.entity.Country;
import br.com.insidesoftwares.dayoffmarker.entity.state.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@EntityGraph(value = "state-full")
	Optional<State> findStateById(Long stateId);

	@EntityGraph(value = "state-full")
    Page<State> findAllByCountry(Country country, Pageable pageable);

    @Query("""
            SELECT count(s)>0
            FROM State s
            WHERE LOWER(s.name) = LOWER(:name) AND
            s.country.id = :countryID AND
            LOWER(s.acronym) = LOWER(:acronym)
            """)
    boolean existsByNameAndCountryIdAndAcronym(String name, Long countryID, String acronym);

    @Query("""
            SELECT count(s)>0
            FROM State s
            WHERE s.country.id = :countryID AND
            LOWER(s.acronym) = LOWER(:acronym)
            """)
    boolean existsByCountryIdAndAcronym(Long countryID, String acronym);

    @Query("""
            SELECT count(s)>0
            FROM State s
            WHERE LOWER(s.name) = LOWER(:name) AND
            s.country.id = :countryID AND
            LOWER(s.acronym) = LOWER(:acronym) AND
            s.id != :stateId
            """)
    boolean existsByNameAndCountryIdAndAcronymAndNotId(String name, Long countryID, String acronym, Long stateId);

    @Query("""
            SELECT count(s)>0
            FROM State s
            WHERE s.country.id = :countryID AND
            LOWER(s.acronym) = LOWER(:acronym) AND
            s.id != :stateId
            """)
    boolean existsByCountryIdAndAcronymAndNotId(Long countryID, String acronym, Long stateId);

	@Query("""
		SELECT count(s) > 0
		FROM State s
		INNER JOIN StateHoliday sh ON s.id =  sh.id.stateId
		INNER JOIN Holiday h ON sh.id.holidayId = h.id
		INNER JOIN Day d ON h.day.id = d.id
		WHERE s = :state
			AND sh.stateHoliday = :stateHoliday
			AND d.date = :dateSearch
		""")
	boolean isStateHolidayByStateAndStateHolidayAndDate(
		final State state,
		final boolean stateHoliday,
		final LocalDate dateSearch
	);


}
