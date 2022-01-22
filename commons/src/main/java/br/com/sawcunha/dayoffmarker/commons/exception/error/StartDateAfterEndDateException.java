package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class StartDateAfterEndDateException extends DayOffMarkerGenericException{
    public StartDateAfterEndDateException() {
        super(eExceptionCode.START_DATE_AFTER_END_DATE.getCode());
    }
}
