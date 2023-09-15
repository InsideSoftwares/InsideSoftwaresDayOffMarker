package br.com.insidesoftwares.dayoffmarker.commons.exception.error.request;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class RequestConflictParametersException extends InsideSoftwaresException {
    public RequestConflictParametersException() {
        super(ExceptionCodeError.REQUEST_CONFLICT_PARAMETERS);
    }
}
