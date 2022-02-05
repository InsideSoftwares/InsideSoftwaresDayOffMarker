package br.com.sawcunha.dayoffmarker.commons.exception.error;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class DayOffMarkerGenericRuntimeException extends RuntimeException{

    private final String code;
	public DayOffMarkerGenericRuntimeException(String code) {
		super();
		this.code = code;
	}

	public DayOffMarkerGenericRuntimeException() {
        super();
        this.code = eExceptionCode.GENERIC.getCode();
    }
}
