package br.com.sawcunha.dayoffmarker.commons.exception.error.tag;


import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class TagNotExistException extends TagException{
	public TagNotExistException() {
		super(eExceptionCode.TAG_NOT_EXIST.getCode());
	}
}
