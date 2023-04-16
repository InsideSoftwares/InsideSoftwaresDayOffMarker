package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CityNotExistException extends InsideSoftwaresException {
    public CityNotExistException() {
        super(ExceptionCodeError.CITY_NOT_EXIST);
    }
}
