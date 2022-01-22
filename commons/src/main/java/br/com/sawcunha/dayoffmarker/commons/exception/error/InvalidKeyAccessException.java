package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class InvalidKeyAccessException extends DayOffMarkerGenericException{
    public InvalidKeyAccessException() {
        super(eExceptionCode.INVALID_KEY_ACCESS.getCode());
    }
}
