package br.com.sawcunha.dayoffmarker.commons.exception.error.city;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CityCodeStateExistException extends CityException {
    public CityCodeStateExistException() {
        super(eExceptionCode.CITY_CODE_STATE_EXIST.getCode());
    }
}
