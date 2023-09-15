package br.com.insidesoftwares.dayoffmarker.commons.exception.error.day;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class DaysNotConfiguredException extends InsideSoftwaresException {
    public DaysNotConfiguredException() {
        super(ExceptionCodeError.DAY_NOT_CONFIGURED);
    }
}
