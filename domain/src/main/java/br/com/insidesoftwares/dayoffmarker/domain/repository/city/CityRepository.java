package br.com.insidesoftwares.dayoffmarker.domain.repository.city;

import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	@EntityGraph(value = "city-full")
	Optional<City> findCityById(Long cityID);

	@EntityGraph(value = "city-full-holiday")
	Optional<City> findCityFullHolidayById(Long cityID);

	@Query("""
            SELECT c FROM City c
            WHERE
            c.state.id = :stateID
            """)
	@EntityGraph(value = "city-full")
    Page<City> findCityByStateID(Long stateID, Pageable pageable);

    @Query("""
            SELECT c FROM City c
            WHERE
            c.state.country = :country
            """)
	@EntityGraph(value = "city-full")
    Page<City> findCityByCountry(Country country, Pageable pageable);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.name) = LOWER(:name)
            """)
    boolean existsByNameAndStateID(String name, Long stateID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code)
            """)
    boolean existsByCodeAndStateID(String code, Long stateID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code) AND
            LOWER(c.acronym) = LOWER(:acronym)
            """)
    boolean existsByCodeAndAcronymAndStateID(String code, String acronym, Long stateID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.name) = LOWER(:name) AND
            c.id != :cityID
            """)
    boolean existsByNameAndStateIDAndNotId(String name, Long stateID, Long cityID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code) AND
            c.id != :cityID
            """)
    boolean existsByCodeAndStateIDAndNotId(String code, Long stateID, Long cityID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code) AND
            LOWER(c.acronym) = LOWER(:acronym) AND
            c.id != :cityID
            """)
    boolean existsByCodeAndAcronymAndStateIDAndNotId(String code, String acronym, Long stateID, Long cityID);
}
