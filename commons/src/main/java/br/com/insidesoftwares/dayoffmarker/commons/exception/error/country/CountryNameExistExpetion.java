package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CountryNameExistExpetion extends InsideSoftwaresException {
    public CountryNameExistExpetion() {
        super(ExceptionCodeError.COUNTRY_NAME_EXIST);

    }
}
