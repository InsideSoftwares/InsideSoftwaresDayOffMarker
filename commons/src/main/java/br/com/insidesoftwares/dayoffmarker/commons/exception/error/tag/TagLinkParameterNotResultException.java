package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class TagLinkParameterNotResultException extends InsideSoftwaresException {
	public TagLinkParameterNotResultException() {
		super(ExceptionCodeError.TAG_LINK_PARAMETER_NOT_RESULT);
	}
}
