package br.com.sawcunha.dayoffmarker.specification.validator;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public interface Validator<I,D> {

    void validator(D dto) throws DayOffMarkerGenericException;
    void validator(I id, D dto) throws DayOffMarkerGenericException;

}
