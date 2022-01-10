package br.com.sawcunha.dayoffmarker.commons.exception.error.city;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CityNameStateExistException extends CityException {
    public CityNameStateExistException() {
        super(eExceptionCode.CITY_NAME_STATE_EXIST.getCode());
    }
}
