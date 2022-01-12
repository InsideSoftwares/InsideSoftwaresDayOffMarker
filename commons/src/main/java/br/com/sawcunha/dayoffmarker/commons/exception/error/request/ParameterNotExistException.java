package br.com.sawcunha.dayoffmarker.commons.exception.error.request;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class ParameterNotExistException extends RequestException {
    public ParameterNotExistException() {
        super(eExceptionCode.PARAMETER_NOT_EXIST.getCode());
    }
}
