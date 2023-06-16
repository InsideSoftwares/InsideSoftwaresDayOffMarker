package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.CountryService;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.entity.Country;
import br.com.insidesoftwares.dayoffmarker.entity.Request;
import br.com.insidesoftwares.dayoffmarker.entity.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.repository.RequestRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class RequestCreationServiceBean implements RequestCreationService {

    private final ConfigurationService configurationService;
    private final RequestRepository requestRepository;
    private final CountryService countryService;
    private final RequestValidator requestValidator;

    @Transactional(rollbackFor = {
            CountryNameInvalidException.class
    })
    @Override
    public String createInitialApplication(final String countryName) {
        UUID keyRequest = UUID.randomUUID();
        Country country = countryService.findCountryByName(countryName);

        Request request = Request.builder()
                .id(keyRequest)
                .createDate(LocalDateTime.now())
                .typeRequest(TypeRequest.REQUEST_CREATION_DATE)
                .statusRequest(StatusRequest.CREATED)
                .requesting("System")
                .build();

        request.setRequestParameter(
                createRequestParameterInitial(request)
        );

        requestRepository.save(request);
        return keyRequest.toString();
    }

    private Set<RequestParameter> createRequestParameterInitial(final Request request) {
        String limitBackYears = configurationService.findValueConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS);
        String limitForwardYears = configurationService.findValueConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS);
        LocalDate baseDate = LocalDate.now();

        String startYear = DateUtils.returnYearMinus(baseDate,limitBackYears);
        String endYear = DateUtils.returnYearPlus(baseDate,limitForwardYears);

        Set<RequestParameter> requestParameters = new HashSet<>();

        requestParameters.add(
                createRequestParameter(request, TypeParameter.START_YEAR, TypeValue.INT, startYear)
        );
        requestParameters.add(
                createRequestParameter(request, TypeParameter.END_YEAR, TypeValue.INT, endYear)
        );

        requestValidator.validRequestInitial(requestParameters);

        return requestParameters;
    }

    private RequestParameter createRequestParameter(
            final Request request,
            final TypeParameter typeParameter,
            final TypeValue typeValue,
            final String value
    ){
        return RequestParameter.builder()
                .typeParameter(typeParameter)
                .typeValue(typeValue)
                .value(value)
                .request(request)
                .build();
    }



}
