package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class MethodNotImplementedException extends InsideSoftwaresException {
    public MethodNotImplementedException() {
        super(ExceptionCodeError.METHOD_NOT_IMPLEMENTED);
    }
}
