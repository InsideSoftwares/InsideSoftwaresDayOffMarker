package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CountryCodeExistExpetion extends InsideSoftwaresException {
    public CountryCodeExistExpetion() {
        super(ExceptionCodeError.COUNTRY_CODE_EXIST);
    }
}
