package br.com.sawcunha.dayoffmarker.commons.exception.error.tag;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;

public class TagException extends DayOffMarkerGenericException {
	public TagException(String code) {
		super(code);
	}
}
