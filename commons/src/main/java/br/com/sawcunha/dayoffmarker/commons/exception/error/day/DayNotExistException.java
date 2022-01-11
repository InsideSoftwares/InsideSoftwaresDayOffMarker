package br.com.sawcunha.dayoffmarker.commons.exception.error.day;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class DayNotExistException extends DayExpetion{
    public DayNotExistException() {
        super(eExceptionCode.DAY_NOT_EXIST.getCode());
    }
}
