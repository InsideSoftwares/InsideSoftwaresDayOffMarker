package br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class HolidayNotExistException extends InsideSoftwaresException {
    public HolidayNotExistException() {
        super(ExceptionCodeError.HOLIDAY_NOT_EXIST);
    }
}
