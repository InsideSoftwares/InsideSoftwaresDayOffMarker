package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CountryNotExistException extends CountryExpetion {
    public CountryNotExistException() {
        super(eExceptionCode.COUNTRY_NOT_EXIST.getCode());
    }
}
