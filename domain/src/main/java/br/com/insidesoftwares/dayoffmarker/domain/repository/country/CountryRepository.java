package br.com.insidesoftwares.dayoffmarker.domain.repository.country;

import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

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
    boolean existsByNameAndNotId(String name, UUID countryId);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.acronym) = LOWER(:acronym) AND
            c.id != :countryId
            """)
    boolean existsByAcronymAndNotId(String acronym, UUID countryId);

    @Query("""
            SELECT count(c)>0
            FROM Country c
            WHERE LOWER(c.code) = LOWER(:code) AND
            c.id != :countryId
            """)
    boolean existsByCodeAndNotId(String code, UUID countryId);
}
