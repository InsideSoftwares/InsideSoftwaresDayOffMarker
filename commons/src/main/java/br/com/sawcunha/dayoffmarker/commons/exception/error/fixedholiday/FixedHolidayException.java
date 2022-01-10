package br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday;

import lombok.Getter;

@Getter
public class FixedHolidayException extends Exception {

    private final String code;
    public FixedHolidayException(final String code) {
        super();
        this.code = code;

    }
}
