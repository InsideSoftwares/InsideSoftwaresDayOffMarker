package br.com.insidesoftwares.dayoffmarker.commons.exception.error.day;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class DayNotExistException extends InsideSoftwaresException {
    public DayNotExistException() {
        super(ExceptionCodeError.DAY_NOT_EXIST);
    }
}
