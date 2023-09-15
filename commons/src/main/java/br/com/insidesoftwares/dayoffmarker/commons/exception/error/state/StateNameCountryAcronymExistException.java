package br.com.insidesoftwares.dayoffmarker.commons.exception.error.state;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class StateNameCountryAcronymExistException extends InsideSoftwaresException {
    public StateNameCountryAcronymExistException() {
        super(ExceptionCodeError.STATE_NAME_COUNTRY_ACRONYM_EXIST);
    }
}
