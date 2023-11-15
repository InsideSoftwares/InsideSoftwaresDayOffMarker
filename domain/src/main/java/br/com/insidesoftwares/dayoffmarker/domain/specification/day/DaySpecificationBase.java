package br.com.insidesoftwares.dayoffmarker.domain.specification.day;

import br.com.insidesoftwares.dayoffmarker.commons.exception.error.EndDateAfterStartDateException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
abstract sealed class DaySpecificationBase permits DaySpecification, PageableDaySpecification {

    protected static final String DAY_ID = "id";
    protected static final String DAY_DATE = "date";
    protected static final String DAY_IS_WEEKEND = "isWeekend";
    protected static final String DAY_IS_HOLIDAY = "isHoliday";
    protected static final String DAY_TAG = "tags";
    protected static final String DAY_TAG_ID = "id";
    protected static final String DAY_TAG_CODE = "code";

    protected static void validStartDateAfterEndDate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException();
        }
    }

    protected static void validEndDateAfterStartDate(final LocalDate endDate, final LocalDate startDate) {
        if (endDate.isAfter(startDate)) {
            throw new EndDateAfterStartDateException();
        }
    }
}
