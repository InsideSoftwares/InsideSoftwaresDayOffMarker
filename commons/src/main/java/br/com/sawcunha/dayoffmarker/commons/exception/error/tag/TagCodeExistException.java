package br.com.sawcunha.dayoffmarker.commons.exception.error.tag;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;

public class TagCodeExistException extends TagException{
	public TagCodeExistException() {
		super(eExceptionCode.TAG_NOT_EXIST.getCode());
	}
}
