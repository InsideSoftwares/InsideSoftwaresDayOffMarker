package br.com.insidesoftwares.dayoffmarker.commons.exception.error.day;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class DaysNotConfiguredException extends InsideSoftwaresException {
    public DaysNotConfiguredException() {
        super(ExceptionCodeError.DAY_NOT_CONFIGURED);
    }
}
