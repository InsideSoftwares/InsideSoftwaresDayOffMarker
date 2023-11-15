package br.com.insidesoftwares.dayoffmarker.domain.repository.state;

import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.QState;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<State, UUID>, JpaSpecificationExecutor<State>, QuerydslPredicateExecutor<State> {

    @EntityGraph(value = "state-full")
    Optional<State> findStateById(UUID stateId);

    @EntityGraph(value = "state-full")
    Page<State> findAllByCountry(Country country, Pageable pageable);

    default boolean existsByNameAndCountryIdAndAcronym(String name, UUID countryID, String acronym) {
        QState qState = QState.state;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qState.name.containsIgnoreCase(name));
        booleanBuilder.and(qState.acronym.containsIgnoreCase(acronym));
        booleanBuilder.and(qState.country.id.eq(countryID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCountryIdAndAcronym(UUID countryID, String acronym) {
        QState qState = QState.state;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qState.acronym.containsIgnoreCase(acronym));
        booleanBuilder.and(qState.country.id.eq(countryID));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByNameAndCountryIdAndAcronymAndNotId(String name, UUID countryID, String acronym, UUID stateId) {
        QState qState = QState.state;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qState.name.containsIgnoreCase(name));
        booleanBuilder.and(qState.acronym.containsIgnoreCase(acronym));
        booleanBuilder.and(qState.country.id.eq(countryID));
        booleanBuilder.and(qState.id.ne(stateId));

        return exists(booleanBuilder.getValue());
    }

    default boolean existsByCountryIdAndAcronymAndNotId(UUID countryID, String acronym, UUID stateId) {
        QState qState = QState.state;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qState.acronym.containsIgnoreCase(acronym));
        booleanBuilder.and(qState.country.id.eq(countryID));
        booleanBuilder.and(qState.id.ne(stateId));

        return exists(booleanBuilder.getValue());
    }

}
