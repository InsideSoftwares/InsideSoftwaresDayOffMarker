package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class DayMonthInvalidException extends DayOffMarkerGenericException{
    public DayMonthInvalidException() {
        super(eExceptionCode.DAY_MONTH_INVALID.getCode());
    }
}
