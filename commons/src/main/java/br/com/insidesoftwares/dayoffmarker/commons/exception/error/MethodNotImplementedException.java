package br.com.insidesoftwares.dayoffmarker.commons.exception.error;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class MethodNotImplementedException extends InsideSoftwaresException {
	public MethodNotImplementedException() {
		super(ExceptionCodeError.METHOD_NOT_IMPLEMENTED);
	}
}
