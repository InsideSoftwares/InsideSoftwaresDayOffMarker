package br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class FixedHolidayNotExistException extends InsideSoftwaresException {
    public FixedHolidayNotExistException() {
        super(ExceptionCodeError.FIXED_HOLIDAY_NOT_EXIST);
    }
}
