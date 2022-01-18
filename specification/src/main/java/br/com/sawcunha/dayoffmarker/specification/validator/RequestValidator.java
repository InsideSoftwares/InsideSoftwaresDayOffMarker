package br.com.sawcunha.dayoffmarker.specification.validator;

import br.com.sawcunha.dayoffmarker.entity.RequestParameter;

import java.util.Set;

public interface RequestValidator {
	void validRequestInitial(final Set<RequestParameter> requestParametersToValidate) throws Exception;
	void validRequestCreateDate(final Set<RequestParameter> requestParametersToValidate) throws Exception;
	void validRequestUpdateHoliday(final Set<RequestParameter> requestParametersToValidate) throws Exception;
}
