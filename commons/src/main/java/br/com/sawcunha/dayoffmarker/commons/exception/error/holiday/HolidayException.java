package br.com.sawcunha.dayoffmarker.commons.exception.error.holiday;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class HolidayException extends DayOffMarkerGenericException {
    public HolidayException(final String code) {
        super(code);
    }
}
