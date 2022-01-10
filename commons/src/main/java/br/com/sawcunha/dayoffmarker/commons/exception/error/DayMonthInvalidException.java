package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class DayMonthInvalidException extends Exception{

    private final String code;
    public DayMonthInvalidException() {
        super();
        this.code = eExceptionCode.INVALID_KEY_ACCESS.getCode();
    }
}
