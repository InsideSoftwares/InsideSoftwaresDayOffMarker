package br.com.sawcunha.dayoffmarker.validator.request;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import br.com.sawcunha.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.request.RequestConflitParametersException;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.entity.RequestParameter;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidatorBean implements RequestValidator {

    private final RequestRepository requestRepository;
    private final LogService<RequestValidatorBean> logService;

    @PostConstruct
    public void init(){
        logService.init(RequestValidatorBean.class);
        logService.logInfor("Init RequestValidatorBean");
    }

    @PreDestroy
    public void destroy(){
        logService.logInfor("End RequestValidatorBean");
    }

    @Transactional(readOnly = true)
    @Override
    public void validRequestInitial(final Set<RequestParameter> requestParametersToValidate) throws Exception {
        try {
            List<Request> requests =
                    requestRepository.findAllRequestByTypeRequestAndNotStatusRequest(
                            eTypeRequest.REQUEST_CREATION_DATE,
                            eStatusRequest.ERROR
                    );

            Integer startYearToValidate = getStartYear(requestParametersToValidate);
            Integer endYearToValidate = getEndYear(requestParametersToValidate);
            Long countryToValidate = getCountry(requestParametersToValidate);

            for (Request request : requests) {
                Set<RequestParameter> requestParameters = request.getRequestParameter();

                Long country = getCountry(requestParameters);
                if(country.equals(countryToValidate)){
                    Integer startYear = getStartYear(requestParameters);
                    Integer endYear = getEndYear(requestParameters);
                    if(startYearToValidate >= startYear && startYearToValidate <= endYear ) throw new RequestConflitParametersException();
                    if(endYearToValidate >= startYear && endYearToValidate <= endYear ) throw new RequestConflitParametersException();
                }
            }
        } catch (ParameterNotExistException | RequestConflitParametersException parameterNotExistExeption){
            logService.logError(
                    parameterNotExistExeption.getCode(),
                    parameterNotExistExeption.getMessage(),
                    parameterNotExistExeption.getCause()
            );
            throw parameterNotExistExeption;
        } catch (Exception e){
            logService.logError(eExceptionCode.GENERIC.getCode(), e.getMessage(), e.getCause());
            throw e;
        }
    }

    private String getParameter(
            final Set<RequestParameter> requestParameters,
            eTypeParameter typeParameter
    ) throws ParameterNotExistException {
        Optional<RequestParameter> requestParameterOptional =
                requestParameters.stream().filter(requestParameter ->
                        requestParameter.getTypeParameter().equals(typeParameter)
        ).findAny();
        return requestParameterOptional.orElseThrow(ParameterNotExistException::new).getValue();
    }

    private Integer getStartYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, eTypeParameter.START_YEAR);
        return Integer.parseInt(year);
    }

    private Integer getEndYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, eTypeParameter.END_YEAR);
        return Integer.parseInt(year);
    }

    private Long getCountry(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String country = getParameter(requestParameters, eTypeParameter.COUNTRY);
        return Long.parseLong(country);
    }

}
