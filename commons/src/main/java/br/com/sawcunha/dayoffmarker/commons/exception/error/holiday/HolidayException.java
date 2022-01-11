package br.com.sawcunha.dayoffmarker.commons.exception.error.holiday;

import lombok.Getter;

@Getter
public class HolidayException extends Exception {

    private final String code;
    public HolidayException(final String code) {
        super();
        this.code = code;
    }
}
