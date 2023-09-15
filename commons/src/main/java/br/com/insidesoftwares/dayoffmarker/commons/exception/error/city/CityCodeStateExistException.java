package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CityCodeStateExistException extends InsideSoftwaresException {
    public CityCodeStateExistException() {
        super(ExceptionCodeError.CITY_CODE_STATE_EXIST);
    }
}
