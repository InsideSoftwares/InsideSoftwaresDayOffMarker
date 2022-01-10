package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class InvalidKeyAccessException extends Exception{

    private final String code;
    public InvalidKeyAccessException() {
        super();
        this.code = eExceptionCode.INVALID_KEY_ACCESS.getCode();
    }
}