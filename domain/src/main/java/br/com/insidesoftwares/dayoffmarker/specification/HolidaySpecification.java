package br.com.insidesoftwares.dayoffmarker.specification;

import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.Holiday;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

public class HolidaySpecification {

	private static final String HOLIDAY_DAY_DATE = "date";
	private static final String HOLIDAY_DAY = "day";

	public static Specification<Holiday> findAllByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate) {
		Specification<Holiday> holidaySpecification = Specification.where(null);
		if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			if (startDate.isAfter(endDate)) {
				throw new StartDateAfterEndDateException();
			}
			holidaySpecification = holidaySpecification.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(HOLIDAY_DAY).get(HOLIDAY_DAY_DATE), startDate, endDate));
		}

		if(Objects.nonNull(startDate) && Objects.isNull(endDate)) {
			holidaySpecification = holidaySpecification.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(HOLIDAY_DAY).get(HOLIDAY_DAY_DATE), startDate));
		}

		if(Objects.isNull(startDate) && Objects.nonNull(endDate)) {
			holidaySpecification = holidaySpecification.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(HOLIDAY_DAY).get(HOLIDAY_DAY_DATE), endDate));
		}

		return holidaySpecification;
	}

}
