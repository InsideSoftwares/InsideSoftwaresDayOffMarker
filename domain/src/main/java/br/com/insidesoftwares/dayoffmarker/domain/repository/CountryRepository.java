package br.com.insidesoftwares.dayoffmarker.domain.repository;

import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("""
            SELECT c
            FROM Country c
            WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name,'%'))
            """)
    Optional<Country> findCountryByName(String name);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.name) = LOWER(:name)
            """)
    boolean existsByName(String name);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.acronym) = LOWER(:acronym)
            """)
    boolean existsByAcronym(String acronym);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.code) = LOWER(:code)
            """)
    boolean existsByCode(String code);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.name) = LOWER(:name) AND
            c.id != :countryId
            """)
    boolean existsByNameAndNotId(String name, Long countryId);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.acronym) = LOWER(:acronym) AND
            c.id != :countryId
            """)
    boolean existsByAcronymAndNotId(String acronym, Long countryId);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.code) = LOWER(:code) AND
            c.id != :countryId
            """)
    boolean existsByCodeAndNotId(String code, Long countryId);
}
