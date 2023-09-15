package br.com.insidesoftwares.dayoffmarker.commons.exception.error.state;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class StateNotExistException extends InsideSoftwaresException {
    public StateNotExistException() {
        super(ExceptionCodeError.STATE_NOT_EXIST);
    }
}
