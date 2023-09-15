package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CountryNameExistExpetion extends InsideSoftwaresException {
    public CountryNameExistExpetion() {
        super(ExceptionCodeError.COUNTRY_NAME_EXIST);

    }
}
