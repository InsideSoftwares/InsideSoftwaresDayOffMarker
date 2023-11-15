package br.com.insidesoftwares.dayoffmarker.domain.specification.state;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.StateHoliday;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationEqual;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StateSpecification {

    private static final String ID = "id";
    private static final String STATE_HOLIDAYS = "stateHolidays";
    private static final String STATE_HOLIDAYS_STATE_HOLIDAY = "stateHoliday";
    private static final String HOLIDAY = "holiday";
    private static final String DAY = "day";
    private static final String DAY_DATE = "date";

    public static Specification<State> findByStateHolidayByStateAndStateHolidayAndDate(
            final UUID stateId,
            final boolean stateHoliday,
            final LocalDate dateSearch
    ) {
        Specification<State> stateSpecification = Specification.where(((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Join<State, StateHoliday> stateHolidayJoin = root.join(STATE_HOLIDAYS, JoinType.INNER);
            Join<StateHoliday, Holiday> holidayJoin = stateHolidayJoin.join(HOLIDAY, JoinType.INNER);
            Join<Holiday, Day> dayJoin = holidayJoin.join(DAY, JoinType.INNER);

            Predicate conditionEqualStateHolidayAndEqualDateSearch = criteriaBuilder.and(
                    criteriaBuilder.equal(stateHolidayJoin.get(STATE_HOLIDAYS_STATE_HOLIDAY), stateHoliday),
                    criteriaBuilder.equal(dayJoin.get(DAY_DATE), dateSearch)
            );

            return criteriaBuilder.and(predicate, conditionEqualStateHolidayAndEqualDateSearch);
        }));

        stateSpecification = stateSpecification.and(specificationEqual(ID, stateId));

        return stateSpecification;
    }

}
