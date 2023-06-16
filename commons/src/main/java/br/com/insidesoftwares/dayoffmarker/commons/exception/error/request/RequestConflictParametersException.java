package br.com.insidesoftwares.dayoffmarker.commons.exception.error.request;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class RequestConflictParametersException extends InsideSoftwaresException {
    public RequestConflictParametersException() {
        super(ExceptionCodeError.REQUEST_CONFLICT_PARAMETERS);
    }
}
