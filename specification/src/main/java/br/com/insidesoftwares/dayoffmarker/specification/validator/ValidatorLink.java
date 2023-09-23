package br.com.insidesoftwares.dayoffmarker.specification.validator;

import org.apache.commons.lang3.NotImplementedException;

public interface ValidatorLink<D> {
	default void validateLink(final Long id, final D dto) {
		throw new NotImplementedException();
	}
	default void validateLinkUnlink(final D dto) {
		throw new NotImplementedException();
	}
}
