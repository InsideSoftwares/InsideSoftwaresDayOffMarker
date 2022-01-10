package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.entity.City;
import br.com.sawcunha.dayoffmarker.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("""
            SELECT c FROM City c
            WHERE
            c.state.id = :stateID
            """)
    Page<City> findCityByStateID(Long stateID, Pageable pageable);

    @Query("""
            SELECT c FROM City c
            WHERE
            c.state.country = :country
            """)
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
