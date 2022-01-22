package br.com.sawcunha.dayoffmarker.specification.validator;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public interface ValidatorLink<I extends Number, D extends DayOffMarkerDTO> {


	void validateLink(I id, D dto) throws DayOffMarkerGenericException;

}
