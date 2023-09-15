package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class ConfigurationNotExistException extends InsideSoftwaresException {
    public ConfigurationNotExistException() {
        super(ExceptionCodeError.CONFIGURATION_NOT_EXIST);
    }
}
