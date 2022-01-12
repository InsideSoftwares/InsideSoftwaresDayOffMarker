package br.com.sawcunha.dayoffmarker.specification.validator;

import br.com.sawcunha.dayoffmarker.entity.RequestParameter;

import java.util.Set;

public interface RequestValidator {
    void validRequestInitial(final Set<RequestParameter> requestParametersToValidate) throws Exception;
}
