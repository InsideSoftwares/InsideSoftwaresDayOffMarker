package br.com.sawcunha.dayoffmarker.commons.exception.error.city;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class CityException extends DayOffMarkerGenericException {
    public CityException(final String code) {
        super(code);
    }
}
