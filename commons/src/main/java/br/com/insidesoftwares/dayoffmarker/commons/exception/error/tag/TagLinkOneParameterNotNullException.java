package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class TagLinkOneParameterNotNullException extends InsideSoftwaresException {
    public TagLinkOneParameterNotNullException() {
        super(ExceptionCodeError.TAG_LINK_ONE_PARAMETER);
    }
}
