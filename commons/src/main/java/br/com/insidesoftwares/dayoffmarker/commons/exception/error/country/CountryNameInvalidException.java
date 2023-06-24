package br.com.insidesoftwares.dayoffmarker.commons.exception.error.country;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class CountryNameInvalidException extends InsideSoftwaresException {
    public CountryNameInvalidException() {
        super(ExceptionCodeError.COUNTRY_NAME_INVALID);
    }
}
