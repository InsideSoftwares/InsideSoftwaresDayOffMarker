package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CountryAcronymExistExpetion extends InsideSoftwaresException {
    public CountryAcronymExistExpetion() {
        super(ExceptionCodeError.COUNTRY_ACRONYM_EXIST);
    }
}
