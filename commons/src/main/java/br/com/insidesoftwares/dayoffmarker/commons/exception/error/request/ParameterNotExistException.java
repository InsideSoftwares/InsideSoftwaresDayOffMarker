package br.com.insidesoftwares.dayoffmarker.commons.exception.error.request;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class ParameterNotExistException extends InsideSoftwaresException {
    public ParameterNotExistException() {
        super(ExceptionCodeError.PARAMETER_NOT_EXIST);
    }
}
