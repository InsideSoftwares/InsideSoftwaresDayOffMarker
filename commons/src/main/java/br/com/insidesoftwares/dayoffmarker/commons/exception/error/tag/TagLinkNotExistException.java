package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class TagLinkNotExistException extends InsideSoftwaresException {
	public TagLinkNotExistException(final Long tagID) {
		super(ExceptionCodeError.TAG_LINK_NOT_EXIST, tagID);
	}
}
