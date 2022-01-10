package br.com.sawcunha.dayoffmarker.commons.exception.error.holiday;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class HolidayNotExistException extends Exception {

    private final String code;
    public HolidayNotExistException() {
        super();
        this.code = eExceptionCode.HOLIDAY_NOT_EXIST.getCode();

    }
}
