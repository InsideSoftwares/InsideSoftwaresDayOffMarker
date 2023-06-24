package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CityNameStateExistException extends InsideSoftwaresException {
    public CityNameStateExistException() {
        super(ExceptionCodeError.CITY_NAME_STATE_EXIST);
    }
}
