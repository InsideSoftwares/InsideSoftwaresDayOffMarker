package br.com.insidesoftwares.dayoffmarker.commons.exception.error.state;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class StateCountryAcronymExistException extends InsideSoftwaresException {
    public StateCountryAcronymExistException() {
        super(ExceptionCodeError.STATE_COUNTRY_ACRONYM_EXIST);
    }
}
