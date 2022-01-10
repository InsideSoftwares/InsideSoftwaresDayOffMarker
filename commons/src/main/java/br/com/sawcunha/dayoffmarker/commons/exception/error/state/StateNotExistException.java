package br.com.sawcunha.dayoffmarker.commons.exception.error.state;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class StateNotExistException extends StateException {
    public StateNotExistException() {
        super(eExceptionCode.STATE_NOT_EXIST.getCode());
    }
}
