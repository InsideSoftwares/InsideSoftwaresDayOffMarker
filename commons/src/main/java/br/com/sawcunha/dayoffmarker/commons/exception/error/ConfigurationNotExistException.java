package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class ConfigurationNotExistException extends DayOffMarkerGenericException{
    public ConfigurationNotExistException() {
        super(eExceptionCode.CONFIGURATION_NOT_EXIST.getCode());
    }
}
