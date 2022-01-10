package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class DayOffMarkerGenericException extends Exception{

    private final String code;
    public DayOffMarkerGenericException() {
        super();
        this.code = eExceptionCode.GENERIC.getCode();
    }
}