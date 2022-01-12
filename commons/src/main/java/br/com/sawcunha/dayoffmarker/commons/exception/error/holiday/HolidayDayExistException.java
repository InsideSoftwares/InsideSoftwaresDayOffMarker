package br.com.sawcunha.dayoffmarker.commons.exception.error.holiday;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class HolidayDayExistException extends HolidayException{
    public HolidayDayExistException() {
        super(eExceptionCode.HOLIDAY_DAY_EXIST.getCode());
    }
}
