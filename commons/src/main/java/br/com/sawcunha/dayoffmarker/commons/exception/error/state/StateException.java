package br.com.sawcunha.dayoffmarker.commons.exception.error.state;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class StateException extends DayOffMarkerGenericException {
    public StateException(String code) {
        super(code);
    }
}
