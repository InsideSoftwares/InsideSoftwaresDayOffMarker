package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import lombok.Getter;

@Getter
public class CountryExpetion extends Exception{

    private final String code;
    public CountryExpetion(String code) {
        super();
        this.code = code;
    }
}
