package br.com.insidesoftwares.dayoffmarker.service.request;

import br.com.insidesoftwares.commons.specification.InsideSoftwaresUserAuthentication;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;

abstract class RequestBean {

    static final String PIPE = "|";
    static final String REQUESTING = "System";

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

}
