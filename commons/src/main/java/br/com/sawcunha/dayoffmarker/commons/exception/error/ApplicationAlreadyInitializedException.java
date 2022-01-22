package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class ApplicationAlreadyInitializedException extends DayOffMarkerGenericException {
    public ApplicationAlreadyInitializedException() {
        super(eExceptionCode.APPLICATION_ALREADY_INITIALIZED.getCode());

    }
}
