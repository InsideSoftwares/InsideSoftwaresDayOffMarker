package br.com.insidesoftwares.dayoffmarker.domain.specification;

import br.com.insidesoftwares.commons.enums.SpecificationFunction;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationBetween;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationEqual;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationGreaterThanOrEqualTo;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationLessThanOrEqualTo;

public class DaySpecification {

    private static final String DAY_DATE = "date";
    private static final String DAY_IS_WEEKEND = "isWeekend";
    private static final String DAY_IS_HOLIDAY = "isHoliday";
	private static final String DAY_OF_WEEK = "dayOfWeek";
	private static final String DAY_OF_YEAR = "dayOfYear";
	private static final String DAY_TAG = "tags";
	private static final String DAY_TAG_ID = "id";
	private static final String DAY_TAG_CODE = "code";

	public static Specification<Day> findAllByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate) {
		Specification<Day> daySpecification = Specification.where(null);
		if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			if (startDate.isAfter(endDate)) {
				throw new StartDateAfterEndDateException();
			}
			daySpecification = daySpecification.and(specificationBetween(DAY_DATE, startDate, endDate));
		}

		if(Objects.nonNull(startDate) && Objects.isNull(endDate)) {
			daySpecification = daySpecification.and(specificationGreaterThanOrEqualTo(DAY_DATE, startDate));
		}

		if(Objects.isNull(startDate) && Objects.nonNull(endDate)) {
			daySpecification = daySpecification.and(specificationLessThanOrEqualTo(DAY_DATE, endDate));
		}

		return daySpecification;
	}

	public static Specification<Day> findDayByDateOrTag(final LocalDate date, final Long tagID, final String tagCode){
		Specification<Day> daySpecification = Specification.where(specificationEqual(DAY_DATE, date));

		if (Objects.nonNull(tagID) && Objects.isNull(tagCode)) {
			daySpecification = daySpecification.and(specificationEqual(tagID, DAY_TAG, DAY_TAG_ID));
		}

		if (Objects.nonNull(tagCode)) {
			daySpecification = daySpecification.and(specificationEqual(tagCode, DAY_TAG, DAY_TAG_CODE));
		}

		return daySpecification;
	}

	public static Specification<Day> exitsDayByTagLinkRequestDTO(final TagLinkRequestDTO tagLinkRequestDTO){
		Specification<Day> daySpecification = Specification.where(null);

		if (Objects.nonNull(tagLinkRequestDTO.dayOfWeek())) {
			daySpecification = daySpecification.and(specificationEqual(DAY_OF_WEEK, tagLinkRequestDTO.dayOfWeek()));
		}

		if (Objects.nonNull(tagLinkRequestDTO.dayOfYear())) {
			daySpecification = daySpecification.and(specificationEqual(DAY_OF_YEAR, tagLinkRequestDTO.dayOfYear()));
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.nonNull(tagLinkRequestDTO.year())
		) {
			LocalDate date = LocalDate.of(tagLinkRequestDTO.year(), tagLinkRequestDTO.month(), tagLinkRequestDTO.day());
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, date));
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.isNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DAY, tagLinkRequestDTO.day()));
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.MONTH, tagLinkRequestDTO.month()));
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.isNull(tagLinkRequestDTO.month()) &&
				Objects.isNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DAY, tagLinkRequestDTO.day()));
		}

		if(
			Objects.isNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.isNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.MONTH, tagLinkRequestDTO.month()));
		}

		if(
			Objects.isNull(tagLinkRequestDTO.day()) &&
				Objects.isNull(tagLinkRequestDTO.month()) &&
				Objects.nonNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.YEAR, tagLinkRequestDTO.year()));
		}

		return daySpecification;
	}

	public static Specification<Day> findAllDayByTagLinkRequestDTO(final TagLinkRequestDTO tagLinkRequestDTO){
		Specification<Day> daySpecification = Specification.where(null);

		if (Objects.nonNull(tagLinkRequestDTO.dayOfWeek())) {
			daySpecification = daySpecification.and(specificationEqual(DAY_OF_WEEK, tagLinkRequestDTO.dayOfWeek()));
		}

		if (Objects.nonNull(tagLinkRequestDTO.dayOfYear())) {
			daySpecification = daySpecification.and(specificationEqual(DAY_OF_YEAR, tagLinkRequestDTO.dayOfYear()));
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.nonNull(tagLinkRequestDTO.year())
		) {
			LocalDate date = LocalDate.of(tagLinkRequestDTO.year(), tagLinkRequestDTO.month(), tagLinkRequestDTO.day());
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, date));
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.isNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DAY, tagLinkRequestDTO.day()));
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.MONTH, tagLinkRequestDTO.month()));
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.isNull(tagLinkRequestDTO.month()) &&
				Objects.isNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DAY, tagLinkRequestDTO.day()));
		}

		if(
			Objects.isNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.isNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.MONTH, tagLinkRequestDTO.month()));
		}

		if(
			Objects.isNull(tagLinkRequestDTO.day()) &&
				Objects.isNull(tagLinkRequestDTO.month()) &&
				Objects.nonNull(tagLinkRequestDTO.year())
		) {
			daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.YEAR, tagLinkRequestDTO.year()));
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

        if(Objects.nonNull(dateStartSearch) && Objects.nonNull(dateFinalSearch) && isSearchForFutureDates) {
            if (dateStartSearch.isAfter(dateFinalSearch)) {
                throw new StartDateAfterEndDateException();
            }
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, dateStartSearch, dateFinalSearch));
        }

        if(Objects.nonNull(dateStartSearch) && Objects.nonNull(dateFinalSearch) && !isSearchForFutureDates) {
            if (dateFinalSearch.isAfter(dateStartSearch)) {
                throw new StartDateAfterEndDateException();
            }
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, dateFinalSearch, dateStartSearch));
        }

        daySpecification = daySpecification.and(specificationEqual(DAY_IS_HOLIDAY, isHoliday));
        daySpecification = daySpecification.and(specificationEqual(DAY_IS_WEEKEND, isWeekend));


        return daySpecification;
    }
}
