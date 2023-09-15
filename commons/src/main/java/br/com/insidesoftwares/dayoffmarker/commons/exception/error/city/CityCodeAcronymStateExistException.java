package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CityCodeAcronymStateExistException extends InsideSoftwaresException {
    public CityCodeAcronymStateExistException() {
        super(ExceptionCodeError.CITY_CODE_ACRONYM_STATE_EXIST);
    }
}
