package br.com.insidesoftwares.dayoffmarker.specification.validator;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;

public interface RequestValidator {
    void validateRequest(final String requestHash, final TypeRequest typeRequest);
}
