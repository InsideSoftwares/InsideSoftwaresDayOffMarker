package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class StartDateAfterEndDateException extends Exception{

    private final String code;
    public StartDateAfterEndDateException() {
        super();
        this.code = eExceptionCode.START_DATE_AFTER_END_DATE.getCode();
    }
}
