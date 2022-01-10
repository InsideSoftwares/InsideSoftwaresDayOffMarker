package br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class FixedHolidayNotExistException extends FixedHolidayException {
    public FixedHolidayNotExistException() {
        super(eExceptionCode.FIXED_HOLIDAY_NOT_EXIST.getCode());
    }
}
