package br.com.sawcunha.dayoffmarker.commons.exception.error.holiday;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class HolidayFromTimeNotInformedException extends HolidayException{
    public HolidayFromTimeNotInformedException() {
        super(eExceptionCode.HOLIDAY_FROM_TIME_NOT_INFORMED_EXIST.getCode());
    }
}
