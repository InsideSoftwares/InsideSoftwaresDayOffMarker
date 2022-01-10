package br.com.sawcunha.dayoffmarker.commons.exception.error.city;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CityCodeAcronymStateExistException extends CityException {
    public CityCodeAcronymStateExistException() {
        super(eExceptionCode.CITY_CODE_ACRONYM_STATE_EXIST.getCode());
    }
}
