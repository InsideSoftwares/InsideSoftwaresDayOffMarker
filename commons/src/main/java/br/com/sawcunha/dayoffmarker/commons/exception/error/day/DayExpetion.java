package br.com.sawcunha.dayoffmarker.commons.exception.error.day;

import lombok.Getter;

@Getter
public class DayExpetion extends Exception{

    private final String code;
    public DayExpetion(String code) {
        super();
        this.code = code;
    }
}
