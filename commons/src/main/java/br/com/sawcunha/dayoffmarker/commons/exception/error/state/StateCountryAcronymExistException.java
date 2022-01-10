package br.com.sawcunha.dayoffmarker.commons.exception.error.state;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class StateCountryAcronymExistException extends StateException {
    public StateCountryAcronymExistException() {
        super(eExceptionCode.STATE_COUNTRY_ACRONYM_EXIST.getCode());
    }
}
