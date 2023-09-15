package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class ApplicationAlreadyInitializedException extends InsideSoftwaresException {
    public ApplicationAlreadyInitializedException() {
        super(ExceptionCodeError.APPLICATION_ALREADY_INITIALIZED);

    }
}
