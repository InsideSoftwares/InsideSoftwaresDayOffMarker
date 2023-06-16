package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CityCodeStateExistException extends InsideSoftwaresException {
    public CityCodeStateExistException() {
        super(ExceptionCodeError.CITY_CODE_STATE_EXIST);
    }
}
