package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CountryCodeExistExpetion extends CountryExpetion {
    public CountryCodeExistExpetion() {
        super(eExceptionCode.COUNTRY_CODE_EXIST.getCode());
    }
}
