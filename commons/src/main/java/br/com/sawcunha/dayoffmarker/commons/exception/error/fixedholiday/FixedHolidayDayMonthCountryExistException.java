package br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class FixedHolidayDayMonthCountryExistException extends FixedHolidayException {
    public FixedHolidayDayMonthCountryExistException() {
        super(eExceptionCode.FIXED_HOLIDAY_DAY_MONTH_COUNTRY_EXIST.getCode());
    }
}
