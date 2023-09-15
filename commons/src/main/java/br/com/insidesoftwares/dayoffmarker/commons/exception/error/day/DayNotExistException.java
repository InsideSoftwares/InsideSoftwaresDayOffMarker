package br.com.insidesoftwares.dayoffmarker.commons.exception.error.day;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class DayNotExistException extends InsideSoftwaresException {
    public DayNotExistException() {
        super(ExceptionCodeError.DAY_NOT_EXIST);
    }
}
