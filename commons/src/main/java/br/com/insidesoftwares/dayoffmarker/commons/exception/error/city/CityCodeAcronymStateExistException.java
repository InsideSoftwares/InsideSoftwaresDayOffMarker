package br.com.insidesoftwares.dayoffmarker.commons.exception.error.city;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CityCodeAcronymStateExistException extends InsideSoftwaresException {
    public CityCodeAcronymStateExistException() {
        super(ExceptionCodeError.CITY_CODE_ACRONYM_STATE_EXIST);
    }
}
