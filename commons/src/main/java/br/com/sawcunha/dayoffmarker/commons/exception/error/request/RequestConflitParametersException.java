package br.com.sawcunha.dayoffmarker.commons.exception.error.request;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class RequestConflitParametersException extends RequestException {
    public RequestConflitParametersException() {
        super(eExceptionCode.REQUEST_CONFLIT_PARAMETERS.getCode());
    }
}
