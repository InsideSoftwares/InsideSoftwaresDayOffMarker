package br.com.insidesoftwares.dayoffmarker.specification.validator;

public interface Validator<I extends Number,D> {
	void validator(final D dto);
	void validator(final I id, final D dto);
	void validator(final I id);
}
