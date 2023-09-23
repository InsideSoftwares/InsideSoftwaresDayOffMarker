package br.com.insidesoftwares.dayoffmarker.job.scheduled.utils;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.Set;

@UtilityClass
public class RequestUtils {

    public static RequestParameter createRequestParameter(
            final Request request,
            final TypeParameter typeParameter,
            final TypeValue typeValue,
            final String value
    ) {
        return RequestParameter.builder()
                .typeParameter(typeParameter)
                .typeValue(typeValue)
                .value(value)
                .request(request)
                .build();
    }

    private static String getParameter(
            final Set<RequestParameter> requestParameters,
            TypeParameter typeParameter
    ) throws ParameterNotExistException {
        Optional<RequestParameter> requestParameterOptional =
                requestParameters.stream().filter(requestParameter ->
                        requestParameter.getTypeParameter().equals(typeParameter)
                ).findAny();
        return requestParameterOptional.orElseThrow(ParameterNotExistException::new).getValue();
    }

    public static Integer getStartYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, TypeParameter.START_YEAR);
        return Integer.parseInt(year);
    }

    public static Integer getEndYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, TypeParameter.END_YEAR);
        return Integer.parseInt(year);
    }

}
