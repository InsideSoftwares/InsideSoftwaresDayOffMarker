package br.com.sawcunha.dayoffmarker.commons.exception.error.holiday;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class HolidayNotExistException extends HolidayException {
    public HolidayNotExistException() {
        super(eExceptionCode.HOLIDAY_NOT_EXIST.getCode());
    }
}
