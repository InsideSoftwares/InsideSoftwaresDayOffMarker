package br.com.sawcunha.dayoffmarker.commons.exception.error.city;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CityNotExistException extends CityException {
    public CityNotExistException() {
        super(eExceptionCode.CITY_NOT_EXIST.getCode());
    }
}
