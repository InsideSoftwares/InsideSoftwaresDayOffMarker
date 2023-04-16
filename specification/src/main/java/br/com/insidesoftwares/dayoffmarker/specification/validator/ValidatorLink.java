package br.com.insidesoftwares.dayoffmarker.specification.validator;

public interface ValidatorLink<I extends Number, D> {


	void validateLink(final I id, final D dto);

}
