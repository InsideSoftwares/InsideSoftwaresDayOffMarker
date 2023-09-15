package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class CountryNameInvalidException extends InsideSoftwaresException {
    public CountryNameInvalidException() {
        super(ExceptionCodeError.COUNTRY_NAME_INVALID);
    }
}
