package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.entity.RequestParameter;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestImplementationService implements RequestService {

    private final ConfigurationImplementationService configurationImplementationService;
    private final RequestRepository requestRepository;

    @Override
    public String createInitialApplication(final String countryName) throws Exception{
        UUID keyRequest = UUID.randomUUID();

        Request request = Request.builder()
                .id(keyRequest)
                .createDate(LocalDateTime.now())
                .typeRequest(eTypeRequest.REQUEST_CREATION_DATE)
                .statusRequest(eStatusRequest.CREATED)
                .requesting("System")
                .build();

        createRequestParameterInitial(request);

        requestRepository.save(request);
        return keyRequest.toString();
    }

    private void createRequestParameterInitial(final Request request) throws Exception {
        String limitBackYears = configurationImplementationService.findValueConfigurationByKey(eConfigurationkey.LIMIT_BACK_DAYS_YEARS);
        String limitForwardYears = configurationImplementationService.findValueConfigurationByKey(eConfigurationkey.LIMIT_FORWARD_DAYS_YEARS);
        LocalDate baseDate = LocalDate.now();

        String startYear = DateUtils.returnYearMinus(baseDate,limitBackYears);
        String endYear = DateUtils.returnYearPlus(baseDate,limitForwardYears);

        Set<RequestParameter> requestParameters = new HashSet<>();

        requestParameters.add(
                createRequestParameter(request, eTypeParameter.START_YEAR, eTypeValue.INT, startYear)
        );
        requestParameters.add(
                createRequestParameter(request, eTypeParameter.END_YEAR, eTypeValue.INT, endYear)
        );
        requestParameters.add(
                createRequestParameter(request, eTypeParameter.COUNTRY, eTypeValue.LONG, "76")
        );

        request.setRequestParameter(requestParameters);

        validRequest(request);
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

    private void validRequest(final Request request) throws Exception{

    }

}
