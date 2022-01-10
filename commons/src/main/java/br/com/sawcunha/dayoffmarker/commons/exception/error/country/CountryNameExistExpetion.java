package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CountryNameExistExpetion extends CountryExpetion {
    public CountryNameExistExpetion() {
        super(eExceptionCode.COUNTRY_NAME_EXIST.getCode());

    }
}
