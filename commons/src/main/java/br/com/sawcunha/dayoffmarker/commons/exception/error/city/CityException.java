package br.com.sawcunha.dayoffmarker.commons.exception.error.city;

import lombok.Getter;

@Getter
public class CityException extends Exception {

    private final String code;
    public CityException(final String code) {
        super();
        this.code = code;

    }
}
