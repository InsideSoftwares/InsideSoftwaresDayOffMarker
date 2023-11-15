package br.com.insidesoftwares.dayoffmarker.domain.specification.holiday;

import br.com.insidesoftwares.commons.enums.SpecificationFunction;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationEqual;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FixedHolidaySpecification {

    private static final String DAY_DATE = "date";
    private static final String DAY_HOLIDAY = "holidays";
    private static final String DAY_HOLIDAY_FIXED_HOLIDAY_ID = "fixedHolidayID";

    public static Specification<Day> findDayWithoutHolidaysByDayAndMonthAndYearAndFixedHolidayIdOrNotHoliday(
            final int day,
            final int month,
            final UUID fixedHolidayID
    ) {
        Specification<Day> daySpecification = Specification.where((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Join<Day, Holiday> holidayJoin = root.join(DAY_HOLIDAY, JoinType.LEFT);

            Predicate conditionFixedHolidayIdOrNotHoliday = criteriaBuilder.or(
                    criteriaBuilder.isNull(holidayJoin.get(DAY_HOLIDAY_FIXED_HOLIDAY_ID)),
                    criteriaBuilder.notEqual(holidayJoin.get(DAY_HOLIDAY_FIXED_HOLIDAY_ID), fixedHolidayID)
            );

            return criteriaBuilder.and(predicate, conditionFixedHolidayIdOrNotHoliday);
        });

        daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_DAY, day));
        daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_MONTH, month));

        return daySpecification;
    }

}
