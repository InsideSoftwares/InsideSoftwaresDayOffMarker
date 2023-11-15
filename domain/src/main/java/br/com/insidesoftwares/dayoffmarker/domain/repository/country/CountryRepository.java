package br.com.insidesoftwares.dayoffmarker.domain.repository.country;

import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.QCountry;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID>, QuerydslPredicateExecutor<Country> {

    default Optional<Country> findCountryByName(String name) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.name.containsIgnoreCase(name));

        return findOne(booleanBuilder.getValue());
    }

    default boolean existsByName(String name) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.name.equalsIgnoreCase(name));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByAcronym(String acronym) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.acronym.equalsIgnoreCase(acronym));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCode(String code) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.code.equalsIgnoreCase(code));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByNameAndNotId(String name, UUID countryId) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.name.equalsIgnoreCase(name));
        booleanBuilder.and(qCountry.id.ne(countryId));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByAcronymAndNotId(String acronym, UUID countryId) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.acronym.equalsIgnoreCase(acronym));
        booleanBuilder.and(qCountry.id.ne(countryId));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCodeAndNotId(String code, UUID countryId) {
        QCountry qCountry = QCountry.country;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qCountry.code.equalsIgnoreCase(code));
        booleanBuilder.and(qCountry.id.ne(countryId));

        return exists(booleanBuilder.getValue());
    }
}
