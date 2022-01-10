package br.com.sawcunha.dayoffmarker.commons.exception.error;

import lombok.Getter;

@Getter
public class TokenJWTException extends Exception{

    private final String code;
    public TokenJWTException(String code) {
        super();
        this.code = code;
    }
}