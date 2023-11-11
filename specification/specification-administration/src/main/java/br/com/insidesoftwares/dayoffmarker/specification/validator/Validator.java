package br.com.insidesoftwares.dayoffmarker.specification.validator;

import java.util.UUID;

public interface Validator<I extends UUID, D> {
    void validator(final D dto);

    void validator(final I id, final D dto);

    void validator(final I id);
}
