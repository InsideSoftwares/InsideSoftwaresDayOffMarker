package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class TagCodeExistException extends InsideSoftwaresException {
    public TagCodeExistException() {
        super(ExceptionCodeError.TAG_NOT_EXIST);
    }
}
