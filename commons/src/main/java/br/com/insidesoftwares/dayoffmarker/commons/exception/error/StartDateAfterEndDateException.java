package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class StartDateAfterEndDateException extends InsideSoftwaresException {
    public StartDateAfterEndDateException() {
        super(ExceptionCodeError.START_DATE_AFTER_END_DATE);
    }
}
