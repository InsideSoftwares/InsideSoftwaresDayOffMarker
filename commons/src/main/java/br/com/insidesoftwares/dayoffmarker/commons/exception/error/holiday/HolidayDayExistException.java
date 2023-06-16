package br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.execption.error.InsideSoftwaresException;

public class HolidayDayExistException extends InsideSoftwaresException {
    public HolidayDayExistException() {
        super(ExceptionCodeError.HOLIDAY_DAY_EXIST);
    }
}
