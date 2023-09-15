package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CityNotExistException extends InsideSoftwaresException {
    public CityNotExistException() {
        super(ExceptionCodeError.CITY_NOT_EXIST);
    }
}
