package br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class FixedHolidayDayMonthCountryExistException extends InsideSoftwaresException {
    public FixedHolidayDayMonthCountryExistException() {
        super(ExceptionCodeError.FIXED_HOLIDAY_DAY_MONTH_COUNTRY_EXIST);
    }
}
