package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class ConfigurationNotExistException extends Exception{

    private final String code;
    public ConfigurationNotExistException() {
        super();
        this.code = eExceptionCode.CONFIGURATION_NOT_EXIST.getCode();
    }
}
