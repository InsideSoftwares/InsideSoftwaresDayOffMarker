package br.com.insidesoftwares.dayoffmarker.service.request;

import br.com.insidesoftwares.commons.specification.InsideSoftwaresUserAuthentication;
import br.com.insidesoftwares.commons.utils.HashUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

abstract class RequestBean {

    static final String PIPE = "|";
    static final String REQUESTING = "System";
    private static final Comparator<RequestParameter> REQUEST_PARAMETER_COMPARATOR = Comparator.comparing(RequestParameter::getTypeParameter)
            .thenComparing(RequestParameter::getTypeValue).thenComparing(RequestParameter::getValue);

    @Autowired
    private InsideSoftwaresUserAuthentication insideSoftwaresUserAuthentication;

    RequestParameter createRequestParameter(
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

    String getUser() {
        return insideSoftwaresUserAuthentication.findUserAuthentication();
    }

    String getHashValues(final Set<RequestParameter> requestParameters, final String typeRequest) {


        String parametersFormatted = requestParameters.stream()
                .sorted(REQUEST_PARAMETER_COMPARATOR)
                .map(RequestParameter::getValue)
                .collect(Collectors.joining(PIPE));

        String values = "%s|%s".formatted(typeRequest, parametersFormatted);
        return HashUtils.createHash(values);
    }

}
