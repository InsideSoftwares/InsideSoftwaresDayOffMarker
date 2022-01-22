package br.com.sawcunha.dayoffmarker.commons.exception.error.tag;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import lombok.Getter;

@Getter
public class TagExistDayException extends TagException{
	private final String args;
	public TagExistDayException(String args) {
		super(eExceptionCode.TAG_EXIST_DAY.getCode());
		this.args = args;
	}
}
