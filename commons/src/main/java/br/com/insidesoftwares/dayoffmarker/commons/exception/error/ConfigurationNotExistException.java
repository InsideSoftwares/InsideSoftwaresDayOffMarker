package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class ConfigurationNotExistException extends InsideSoftwaresException {
    public ConfigurationNotExistException() {
        super(ExceptionCodeError.CONFIGURATION_NOT_EXIST);
    }
}
