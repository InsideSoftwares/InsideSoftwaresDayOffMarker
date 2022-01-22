package br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class FixedHolidayException extends DayOffMarkerGenericException {
    public FixedHolidayException(final String code) {
        super(code);
    }
}
