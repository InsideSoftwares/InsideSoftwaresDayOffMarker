package br.com.sawcunha.dayoffmarker.commons.exception.error.country;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class CountryExpetion extends DayOffMarkerGenericException {
    public CountryExpetion(String code) {
        super(code);
    }
}
