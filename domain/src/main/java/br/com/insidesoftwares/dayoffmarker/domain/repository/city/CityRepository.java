package br.com.insidesoftwares.dayoffmarker.domain.repository.city;

import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    @EntityGraph(value = "city-full")
    Optional<City> findCityById(UUID cityID);

    @EntityGraph(value = "city-full-holiday")
    Optional<City> findCityFullHolidayById(UUID cityID);

    @Query("""
            SELECT c FROM City c
            WHERE
            c.state.id = :stateID
            """)
    @EntityGraph(value = "city-full")
    Page<City> findCityByStateID(UUID stateID, Pageable pageable);

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
    boolean existsByNameAndStateID(String name, UUID stateID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code)
            """)
    boolean existsByCodeAndStateID(String code, UUID stateID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code) AND
            LOWER(c.acronym) = LOWER(:acronym)
            """)
    boolean existsByCodeAndAcronymAndStateID(String code, String acronym, UUID stateID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.name) = LOWER(:name) AND
            c.id != :cityID
            """)
    boolean existsByNameAndStateIDAndNotId(String name, UUID stateID, UUID cityID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code) AND
            c.id != :cityID
            """)
    boolean existsByCodeAndStateIDAndNotId(String code, UUID stateID, UUID cityID);

    @Query("""
            SELECT count(c)>0
            FROM City c
            WHERE c.state.id = :stateID AND
            LOWER(c.code) = LOWER(:code) AND
            LOWER(c.acronym) = LOWER(:acronym) AND
            c.id != :cityID
            """)
    boolean existsByCodeAndAcronymAndStateIDAndNotId(String code, String acronym, UUID stateID, UUID cityID);
}
