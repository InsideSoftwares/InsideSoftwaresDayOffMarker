package br.com.insidesoftwares.dayoffmarker.specification.validator;

import br.com.insidesoftwares.dayoffmarker.entity.request.RequestParameter;

import java.util.Set;

public interface RequestValidator {
	void validRequestInitial(final Set<RequestParameter> requestParametersToValidate);
	void validRequestCreateDate(final Set<RequestParameter> requestParametersToValidate);
	void validRequestUpdateHoliday(final Set<RequestParameter> requestParametersToValidate);
}
