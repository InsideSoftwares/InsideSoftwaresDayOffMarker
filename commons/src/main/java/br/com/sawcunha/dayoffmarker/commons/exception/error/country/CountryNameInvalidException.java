package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CountryNameInvalidException extends CountryExpetion {
    public CountryNameInvalidException() {
        super(eExceptionCode.COUNTRY_NAME_INVALID.getCode());
    }
}