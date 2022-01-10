package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class CountryAcronymExistExpetion extends CountryExpetion {
    public CountryAcronymExistExpetion() {
        super(eExceptionCode.COUNTRY_ACRONYM_EXIST.getCode());
    }
}
