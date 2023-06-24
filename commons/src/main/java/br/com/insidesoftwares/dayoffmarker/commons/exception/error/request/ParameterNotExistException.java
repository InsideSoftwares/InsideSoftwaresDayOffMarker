package br.com.insidesoftwares.dayoffmarker.commons.exception.error.request;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class ParameterNotExistException extends InsideSoftwaresException {
    public ParameterNotExistException() {
        super(ExceptionCodeError.PARAMETER_NOT_EXIST);
    }
}
