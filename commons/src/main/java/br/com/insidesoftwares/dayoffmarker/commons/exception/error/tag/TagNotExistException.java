package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;


import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;

public class TagNotExistException extends InsideSoftwaresException {
    public TagNotExistException() {
        super(ExceptionCodeError.TAG_NOT_EXIST);
    }
}
