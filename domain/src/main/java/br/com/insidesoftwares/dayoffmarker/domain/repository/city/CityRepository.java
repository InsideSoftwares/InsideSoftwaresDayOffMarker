package br.com.insidesoftwares.dayoffmarker.domain.repository.city;

import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.QCity;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID>, QuerydslPredicateExecutor<City> {

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

    default boolean existsByNameAndStateID(String name, UUID stateID) {
        QCity qCity = QCity.city;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCity.name.containsIgnoreCase(name));
        booleanBuilder.and(qCity.state.id.eq(stateID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCodeAndStateID(String code, UUID stateID) {
        QCity qCity = QCity.city;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCity.code.containsIgnoreCase(code));
        booleanBuilder.and(qCity.state.id.eq(stateID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCodeAndAcronymAndStateID(String code, String acronym, UUID stateID) {
        QCity qCity = QCity.city;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCity.code.containsIgnoreCase(code));
        booleanBuilder.and(qCity.acronym.containsIgnoreCase(acronym));
        booleanBuilder.and(qCity.state.id.eq(stateID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByNameAndStateIDAndNotId(String name, UUID stateID, UUID cityID) {
        QCity qCity = QCity.city;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCity.name.containsIgnoreCase(name));
        booleanBuilder.and(qCity.state.id.eq(stateID));
        booleanBuilder.and(qCity.id.ne(cityID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCodeAndStateIDAndNotId(String code, UUID stateID, UUID cityID) {
        QCity qCity = QCity.city;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCity.code.containsIgnoreCase(code));
        booleanBuilder.and(qCity.state.id.eq(stateID));
        booleanBuilder.and(qCity.id.ne(cityID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCodeAndAcronymAndStateIDAndNotId(String code, String acronym, UUID stateID, UUID cityID) {
        QCity qCity = QCity.city;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCity.code.containsIgnoreCase(code));
        booleanBuilder.and(qCity.acronym.containsIgnoreCase(acronym));
        booleanBuilder.and(qCity.state.id.eq(stateID));
        booleanBuilder.and(qCity.id.ne(cityID));

        return exists(booleanBuilder.getValue());
    }
}
