package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class DayMonthInvalidException extends InsideSoftwaresException {
    public DayMonthInvalidException() {
        super(ExceptionCodeError.DAY_MONTH_INVALID);
    }
}
