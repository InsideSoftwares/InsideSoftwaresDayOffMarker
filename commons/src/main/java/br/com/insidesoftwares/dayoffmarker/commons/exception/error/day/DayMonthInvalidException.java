package br.com.insidesoftwares.dayoffmarker.commons.exception.error.day;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class DayMonthInvalidException extends InsideSoftwaresException {
    public DayMonthInvalidException() {
        super(ExceptionCodeError.DAY_MONTH_INVALID);
    }
}
