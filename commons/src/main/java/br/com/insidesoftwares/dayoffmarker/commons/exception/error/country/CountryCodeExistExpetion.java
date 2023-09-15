package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CountryCodeExistExpetion extends InsideSoftwaresException {
    public CountryCodeExistExpetion() {
        super(ExceptionCodeError.COUNTRY_CODE_EXIST);
    }
}
