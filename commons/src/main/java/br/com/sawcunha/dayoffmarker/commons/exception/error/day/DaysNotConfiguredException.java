package br.com.sawcunha.dayoffmarker.commons.exception.error.day;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class DaysNotConfiguredException extends DayExpetion{
    public DaysNotConfiguredException() {
        super(eExceptionCode.DAY_NOT_CONFIGURED.getCode());
    }
}
