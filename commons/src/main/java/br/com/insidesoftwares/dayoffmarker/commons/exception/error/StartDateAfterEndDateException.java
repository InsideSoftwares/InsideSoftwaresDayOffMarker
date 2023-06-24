package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class StartDateAfterEndDateException extends InsideSoftwaresException {
    public StartDateAfterEndDateException() {
        super(ExceptionCodeError.START_DATE_AFTER_END_DATE);
    }
}
