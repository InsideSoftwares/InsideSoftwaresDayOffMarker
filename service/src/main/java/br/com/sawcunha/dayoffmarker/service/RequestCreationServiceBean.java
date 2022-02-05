package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.entity.RequestParameter;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.RequestCreationService;
import br.com.sawcunha.dayoffmarker.specification.validator.RequestValidator;
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

    private final ConfigurationServiceBean configurationServiceBean;
    private final RequestRepository requestRepository;
    private final CountryService countryService;
    private final RequestValidator requestValidator;

    @Transactional(rollbackFor = {
            CountryNameInvalidException.class
    })
    @Override
    public String createInitialApplication(final String countryName) throws DayOffMarkerGenericException {
        UUID keyRequest = UUID.randomUUID();
        Country country = countryService.findCountryByName(countryName);

        Request request = Request.builder()
                .id(keyRequest)
                .createDate(LocalDateTime.now())
                .typeRequest(eTypeRequest.REQUEST_CREATION_DATE)
                .statusRequest(eStatusRequest.CREATED)
                .requesting("System")
                .build();

        request.setRequestParameter(
                createRequestParameterInitial(request, country)
        );

        requestRepository.save(request);
        return keyRequest.toString();
    }

    private Set<RequestParameter> createRequestParameterInitial(final Request request, final Country country) throws DayOffMarkerGenericException {
        String limitBackYears = configurationServiceBean.findValueConfigurationByKey(eConfigurationkey.LIMIT_BACK_DAYS_YEARS);
        String limitForwardYears = configurationServiceBean.findValueConfigurationByKey(eConfigurationkey.LIMIT_FORWARD_DAYS_YEARS);
        LocalDate baseDate = LocalDate.now();

        String startYear = DateUtils.returnYearMinus(baseDate,limitBackYears);
        String endYear = DateUtils.returnYearPlus(baseDate,limitForwardYears);

        Set<RequestParameter> requestParameters = new HashSet<>();

        requestParameters.add(
                createRequestParameter(request, eTypeParameter.COUNTRY, eTypeValue.LONG, country.getId().toString())
        );
        requestParameters.add(
                createRequestParameter(request, eTypeParameter.START_YEAR, eTypeValue.INT, startYear)
        );
        requestParameters.add(
                createRequestParameter(request, eTypeParameter.END_YEAR, eTypeValue.INT, endYear)
        );

        requestValidator.validRequestInitial(requestParameters);

        return requestParameters;
    }

    private RequestParameter createRequestParameter(
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



}
