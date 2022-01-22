package br.com.sawcunha.dayoffmarker.commons.exception.error.request;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class RequestException extends DayOffMarkerGenericException {
    public RequestException(final String code) {
        super(code);
    }
}
