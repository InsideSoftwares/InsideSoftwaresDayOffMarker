package br.com.insidesoftwares.dayoffmarker.specification;

import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

public class DaySpecification {

	private static final String DAY_DATE = "date";
	private static final String DAY_TAG = "tags";
	private static final String DAY_TAG_ID = "id";
	private static final String DAY_TAG_CODE = "code";

	public static Specification<Day> findAllByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate) {
		Specification<Day> daySpecification = Specification.where(null);
		if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			if (startDate.isAfter(endDate)) {
				throw new StartDateAfterEndDateException();
			}
			daySpecification = daySpecification.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(DAY_DATE), startDate, endDate));
		}

		if(Objects.nonNull(startDate) && Objects.isNull(endDate)) {
			daySpecification = daySpecification.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(DAY_DATE), startDate));
		}

		if(Objects.isNull(startDate) && Objects.nonNull(endDate)) {
			daySpecification = daySpecification.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(DAY_DATE), endDate));
		}

		return daySpecification;
	}

	public static Specification<Day> findDayByDateOrTag(final LocalDate date, final Long tagID, final String tagCode){
		Specification<Day> daySpecification = Specification.where(
			(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DAY_DATE), date)
		);

		if (Objects.nonNull(tagID) && Objects.isNull(tagCode)) {
			daySpecification = daySpecification.and(
				(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DAY_TAG).get(DAY_TAG_ID),tagID)
			);
		}

		if (Objects.nonNull(tagCode)) {
			daySpecification = daySpecification.and(
				(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DAY_TAG).get(DAY_TAG_CODE),tagCode)
			);
		}

		return daySpecification;
	}

}
