package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class MethodNotImplementedException extends DayOffMarkerGenericException{
	public MethodNotImplementedException() {
		super(eExceptionCode.METHOD_NOT_IMPLEMENTED.getCode());
	}
}
