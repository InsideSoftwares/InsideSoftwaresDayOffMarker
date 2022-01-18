package br.com.sawcunha.dayoffmarker.commons.exception.error.request;

import lombok.Getter;

@Getter
public class RequestException extends Exception{

    private final String code;
    public RequestException(final String code) {
        super();
        this.code = code;
    }
}
