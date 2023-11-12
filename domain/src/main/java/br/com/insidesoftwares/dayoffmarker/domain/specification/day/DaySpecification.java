package br.com.insidesoftwares.dayoffmarker.domain.specification.day;

import br.com.insidesoftwares.commons.enums.SpecificationFunction;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationBetween;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationEqual;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationGreaterThanOrEqualTo;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationLessThanOrEqualTo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DaySpecification {

    private static final String DAY_ID = "id";
    private static final String DAY_DATE = "date";
    private static final String DAY_IS_WEEKEND = "isWeekend";
    private static final String DAY_IS_HOLIDAY = "isHoliday";
    private static final String DAY_TAG = "tags";
    private static final String DAY_TAG_ID = "id";
    private static final String DAY_TAG_CODE = "code";
    private static final String DAY_HOLIDAY = "holidays";
    private static final String DAY_HOLIDAY_FIXED_HOLIDAY_ID = "fixedHolidayID";

    public static Specification<Day> findAllByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate) {
        Specification<Day> daySpecification = Specification.where(null);
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            if (startDate.isAfter(endDate)) {
                throw new StartDateAfterEndDateException();
            }
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, startDate, endDate));
        }

        if (Objects.nonNull(startDate) && Objects.isNull(endDate)) {
            daySpecification = daySpecification.and(specificationGreaterThanOrEqualTo(DAY_DATE, startDate));
        }

        if (Objects.isNull(startDate) && Objects.nonNull(endDate)) {
            daySpecification = daySpecification.and(specificationLessThanOrEqualTo(DAY_DATE, endDate));
        }

        return daySpecification;
    }

    public static Specification<Day> findDayByDateOrTag(final LocalDate date, final UUID tagID, final String tagCode) {
        Specification<Day> daySpecification = Specification.where(specificationEqual(DAY_DATE, date));

        if (Objects.nonNull(tagID) && Objects.isNull(tagCode)) {
            daySpecification = daySpecification.and(specificationEqual(tagID, DAY_TAG, DAY_TAG_ID));
        }

        if (Objects.nonNull(tagCode)) {
            daySpecification = daySpecification.and(specificationEqual(tagCode, DAY_TAG, DAY_TAG_CODE));
        }

        return daySpecification;
    }



    public static Specification<Day> findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
            final LocalDate dateStartSearch,
            final LocalDate dateFinalSearch,
            final boolean isHoliday,
            final boolean isWeekend,
            final boolean isSearchForFutureDates
    ) {
        Specification<Day> daySpecification = Specification.where(null);

        if (Objects.nonNull(dateStartSearch) && Objects.nonNull(dateFinalSearch) && isSearchForFutureDates) {
            if (dateStartSearch.isAfter(dateFinalSearch)) {
                throw new StartDateAfterEndDateException();
            }
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, dateStartSearch, dateFinalSearch));
        }

        if (Objects.nonNull(dateStartSearch) && Objects.nonNull(dateFinalSearch) && !isSearchForFutureDates) {
            if (dateFinalSearch.isAfter(dateStartSearch)) {
                throw new StartDateAfterEndDateException();
            }
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, dateFinalSearch, dateStartSearch));
        }

        daySpecification = daySpecification.and(specificationEqual(DAY_IS_HOLIDAY, isHoliday));
        daySpecification = daySpecification.and(specificationEqual(DAY_IS_WEEKEND, isWeekend));


        return daySpecification;
    }

    public static Specification<Day> findWorkingDayByDateAndIsHolidayAndIsWeekend(
            final LocalDate date,
            final Boolean isHoliday,
            final Boolean isWeekend
    ) {
        Specification<Day> daySpecification = Specification.where(specificationEqual(DAY_DATE, date));

        if(Objects.nonNull(isHoliday)) {
            daySpecification = daySpecification.and(specificationEqual(DAY_IS_HOLIDAY, isHoliday));
        }
        if(Objects.nonNull(isWeekend)) {
            daySpecification = daySpecification.and(specificationEqual(DAY_IS_WEEKEND, isWeekend));
        }

        return daySpecification;
    }

    public static Specification<Day> findDayByDayIdAndTagIds(final UUID dayID, final Set<UUID> tagsID) {
        Specification<Day> daySpecification = Specification.where(specificationEqual(DAY_ID, dayID));

        daySpecification = daySpecification.and((root, query, criteriaBuilder) -> root.get(DAY_TAG).get(DAY_TAG_ID).in(tagsID));

        return daySpecification;
    }

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
