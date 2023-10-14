package br.com.insidesoftwares.dayoffmarker.specification.validator;

import jakarta.el.MethodNotFoundException;

public interface ValidatorLink<D> {
    default void validateLink(final Long id, final D dto) {
        throw new MethodNotFoundException();
    }

    default void validateLinkUnlink(final D dto) {
        throw new MethodNotFoundException();
    }
}
