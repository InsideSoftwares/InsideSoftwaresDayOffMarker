package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class ApplicationAlreadyInitializedException extends InsideSoftwaresException {
    public ApplicationAlreadyInitializedException() {
        super(ExceptionCodeError.APPLICATION_ALREADY_INITIALIZED);

    }
}
