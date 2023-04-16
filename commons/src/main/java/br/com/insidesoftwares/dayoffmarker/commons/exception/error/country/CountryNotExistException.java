package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CountryNotExistException extends InsideSoftwaresException {
    public CountryNotExistException() {
        super(ExceptionCodeError.COUNTRY_NOT_EXIST);
    }
}
