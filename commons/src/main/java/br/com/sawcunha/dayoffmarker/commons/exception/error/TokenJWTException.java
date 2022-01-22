package br.com.sawcunha.dayoffmarker.commons.exception.error;

public class TokenJWTException extends DayOffMarkerGenericException{
    public TokenJWTException(String code) {
        super(code);
    }
}
