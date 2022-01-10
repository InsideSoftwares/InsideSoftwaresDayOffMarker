package br.com.sawcunha.dayoffmarker.commons.exception.error.state;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class StateNameCountryAcronymExistException extends StateException {
    public StateNameCountryAcronymExistException() {
        super(eExceptionCode.STATE_NAME_COUNTRY_ACRONYM_EXIST.getCode());
    }
}
