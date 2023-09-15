package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CountryNotExistException extends InsideSoftwaresException {
    public CountryNotExistException() {
        super(ExceptionCodeError.COUNTRY_NOT_EXIST);
    }
}
