package br.com.insidesoftwares.dayoffmarker.job;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;

import java.util.Optional;
import java.util.Set;

public abstract class RequestJobBean {

    public RequestParameter createRequestParameter(
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

    private String getParameter(
            final Set<RequestParameter> requestParameters,
            TypeParameter typeParameter
    ) throws ParameterNotExistException {
        Optional<RequestParameter> requestParameterOptional =
                requestParameters.stream().filter(requestParameter ->
                        requestParameter.getTypeParameter().equals(typeParameter)
                ).findAny();
        return requestParameterOptional.orElseThrow(ParameterNotExistException::new).getValue();
    }

    public Integer getStartYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, TypeParameter.START_YEAR);
        return Integer.parseInt(year);
    }

    public Integer getEndYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, TypeParameter.END_YEAR);
        return Integer.parseInt(year);
    }

}
