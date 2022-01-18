package br.com.sawcunha.dayoffmarker.scheduled.utils;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
import br.com.sawcunha.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.entity.RequestParameter;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.Set;

@UtilityClass
public class RequestUtils {

	public static RequestParameter createRequestParameter(
			final Request request,
			final eTypeParameter typeParameter,
			final eTypeValue typeValue,
			final String value
	){
		return RequestParameter.builder()
				.typeParameter(typeParameter)
				.typeValue(typeValue)
				.value(value)
				.request(request)
				.build();
	}

	private static String getParameter(
			final Set<RequestParameter> requestParameters,
			eTypeParameter typeParameter
	) throws ParameterNotExistException {
		Optional<RequestParameter> requestParameterOptional =
				requestParameters.stream().filter(requestParameter ->
						requestParameter.getTypeParameter().equals(typeParameter)
				).findAny();
		return requestParameterOptional.orElseThrow(ParameterNotExistException::new).getValue();
	}

	public static Integer getStartYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
		String year = getParameter(requestParameters, eTypeParameter.START_YEAR);
		return Integer.parseInt(year);
	}

	public static Integer getEndYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
		String year = getParameter(requestParameters, eTypeParameter.END_YEAR);
		return Integer.parseInt(year);
	}

	public static Long getCountry(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
		String country = getParameter(requestParameters, eTypeParameter.COUNTRY);
		return Long.parseLong(country);
	}

}
