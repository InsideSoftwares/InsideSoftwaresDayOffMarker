package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("""
            SELECT h FROM Holiday h
            WHERE h.day.country = :country
            """)
    Page<Holiday> findAllByCountry(Country country, Pageable pageable);

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
