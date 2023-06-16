package br.com.insidesoftwares.dayoffmarker.job.utils.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.entity.RequestParameter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class RequestParametersUtils {

	private static RequestParameter getParameter(
			final Set<RequestParameter> RequestParameterS,
			final TypeParameter typeParameter){
		Optional<RequestParameter> optionalRequestParameter =
				RequestParameterS.stream().filter(RequestParameter ->
					RequestParameter.getTypeParameter().equals(typeParameter)
			).findAny();
        return optionalRequestParameter.orElse(null);
    }

    public static Integer getYear(Set<RequestParameter> RequestParameterS){
		RequestParameter RequestParameter = getParameter(RequestParameterS, TypeParameter.YEAR);
        return Objects.nonNull(RequestParameter) ? Integer.parseInt(RequestParameter.getValue()) : null;
    }

	public static Integer getMonth(Set<RequestParameter> RequestParameterS){
		RequestParameter RequestParameter = getParameter(RequestParameterS, TypeParameter.MONTH);
		return Objects.nonNull(RequestParameter) ? Integer.parseInt(RequestParameter.getValue()) : null;
    }

	public static Long getFixedHolidayID(Set<RequestParameter> RequestParameterS){
		RequestParameter RequestParameter = getParameter(RequestParameterS, TypeParameter.FIXED_HOLIDAY_ID);
		return Objects.nonNull(RequestParameter) ? Long.parseLong(RequestParameter.getValue()) : null;
	}
}
