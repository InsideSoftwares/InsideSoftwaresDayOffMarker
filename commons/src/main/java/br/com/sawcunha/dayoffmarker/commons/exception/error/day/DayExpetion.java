package br.com.sawcunha.dayoffmarker.commons.exception.error.day;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class DayExpetion extends DayOffMarkerGenericException {
    public DayExpetion(String code) {
        super(code);
    }
}
