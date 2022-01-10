package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class ApplicationAlreadyInitializedException extends Exception {

    private final String code;
    public ApplicationAlreadyInitializedException() {
        super();
        this.code = eExceptionCode.APPLICATION_ALREADY_INITIALIZED.getCode();

    }
}