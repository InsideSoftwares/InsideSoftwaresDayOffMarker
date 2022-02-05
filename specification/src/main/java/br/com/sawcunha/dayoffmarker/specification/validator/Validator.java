package br.com.sawcunha.dayoffmarker.specification.validator;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public interface Validator<I extends Number,D extends DayOffMarkerDTO> {

	void validator(final D dto) throws DayOffMarkerGenericException;
	void validator(final I id, final D dto) throws DayOffMarkerGenericException;
	void validator(final I id) throws DayOffMarkerGenericException;

}
