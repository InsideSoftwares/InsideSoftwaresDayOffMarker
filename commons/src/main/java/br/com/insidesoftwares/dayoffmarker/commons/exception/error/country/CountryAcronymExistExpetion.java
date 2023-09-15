package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CountryAcronymExistExpetion extends InsideSoftwaresException {
    public CountryAcronymExistExpetion() {
        super(ExceptionCodeError.COUNTRY_ACRONYM_EXIST);
    }
}
