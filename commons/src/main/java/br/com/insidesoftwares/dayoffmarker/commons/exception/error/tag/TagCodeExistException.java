package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;

public class TagCodeExistException extends InsideSoftwaresException {
	public TagCodeExistException() {
		super(ExceptionCodeError.TAG_NOT_EXIST);
	}
}
