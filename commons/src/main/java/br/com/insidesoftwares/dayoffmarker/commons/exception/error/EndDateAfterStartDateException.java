package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class EndDateAfterStartDateException extends InsideSoftwaresException {
    public EndDateAfterStartDateException() {
        super(ExceptionCodeError.START_DATE_AFTER_END_DATE);
    }
}
