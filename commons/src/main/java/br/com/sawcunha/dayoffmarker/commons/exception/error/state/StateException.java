package br.com.sawcunha.dayoffmarker.commons.exception.error.state;

import lombok.Getter;

@Getter
public class StateException extends Exception {

    private final String code;
    public StateException(String code) {
        super();
        this.code = code;

    }
}
