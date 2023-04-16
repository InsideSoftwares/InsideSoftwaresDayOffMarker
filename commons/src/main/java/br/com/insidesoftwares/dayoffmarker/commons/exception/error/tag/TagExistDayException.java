package br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag;

import br.com.insidesoftwares.execption.error.InsideSoftwaresException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import lombok.Getter;

@Getter
public class TagExistDayException extends InsideSoftwaresException {
	public TagExistDayException(String args) {
		super(ExceptionCodeError.TAG_EXIST_DAY, args);
	}
}
