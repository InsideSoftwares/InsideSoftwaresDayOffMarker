package br.com.sawcunha.dayoffmarker.commons.exception.error.tag;

import lombok.Getter;

@Getter
public class TagException extends Exception {
	private final String code;
	public TagException(String code) {
		super();
		this.code = code;

	}
}
