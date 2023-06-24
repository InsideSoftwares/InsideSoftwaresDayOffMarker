package br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class HolidayFromTimeNotInformedException extends InsideSoftwaresException {
    public HolidayFromTimeNotInformedException() {
        super(ExceptionCodeError.HOLIDAY_FROM_TIME_NOT_INFORMED_EXIST);
    }
}
