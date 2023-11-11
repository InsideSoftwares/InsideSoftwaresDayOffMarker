package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

import java.util.UUID;

public class TagLinkNotExistException extends InsideSoftwaresException {
    public TagLinkNotExistException(final UUID tagID) {
        super(ExceptionCodeError.TAG_LINK_NOT_EXIST, tagID);
    }
}
