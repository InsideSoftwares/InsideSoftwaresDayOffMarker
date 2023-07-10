package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class TagLinkDateInvalidException extends InsideSoftwaresException {
	public TagLinkDateInvalidException() {
		super(ExceptionCodeError.TAG_LINK_DATE_INVALID);
	}
}
