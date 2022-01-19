package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

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

}
