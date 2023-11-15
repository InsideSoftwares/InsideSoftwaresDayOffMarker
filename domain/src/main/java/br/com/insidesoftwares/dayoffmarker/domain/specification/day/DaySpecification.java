package br.com.insidesoftwares.dayoffmarker.domain.specification.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationBetween;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationEqual;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DaySpecification extends DaySpecificationBase {

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
            validStartDateAfterEndDate(dateStartSearch, dateFinalSearch);
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, dateStartSearch, dateFinalSearch));
        }

        if (Objects.nonNull(dateStartSearch) && Objects.nonNull(dateFinalSearch) && !isSearchForFutureDates) {
            validEndDateAfterStartDate(dateFinalSearch, dateStartSearch);
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
}
