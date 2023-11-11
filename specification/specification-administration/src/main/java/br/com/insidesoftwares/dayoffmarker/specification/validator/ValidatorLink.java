package br.com.insidesoftwares.dayoffmarker.specification.validator;

import jakarta.el.MethodNotFoundException;

import java.util.UUID;

public interface ValidatorLink<D> {
    default void validateLink(final UUID id, final D dto) {
        throw new MethodNotFoundException();
    }

    default void validateLinkUnlink(final D dto) {
        throw new MethodNotFoundException();
    }
}
