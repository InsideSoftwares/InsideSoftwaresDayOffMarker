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
import java.util.Objects;
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

            Integer startYearToValidate = getInteger(requestParametersToValidate, eTypeParameter.START_YEAR);
            Integer endYearToValidate = getInteger(requestParametersToValidate, eTypeParameter.END_YEAR);
            Long countryToValidate = getLong(requestParametersToValidate, eTypeParameter.COUNTRY);

            for (Request request : requests) {
                Set<RequestParameter> requestParameters = request.getRequestParameter();

                Long country = getLong(requestParameters, eTypeParameter.COUNTRY);
                if(country.equals(countryToValidate)){
                    Integer startYear = getInteger(requestParameters,  eTypeParameter.START_YEAR);
                    Integer endYear = getInteger(requestParameters, eTypeParameter.END_YEAR);
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

	@Transactional(readOnly = true)
	@Override
	public void validRequestCreateDate(final Set<RequestParameter> requestParametersToValidate) throws Exception {
		try {
			List<Request> requests =
					requestRepository.findAllRequestByTypeRequestAndNotStatusRequest(
							eTypeRequest.CREATE_DATE,
							eStatusRequest.ERROR
					);

			Integer yearToValidate = getInteger(requestParametersToValidate, eTypeParameter.YEAR);
			Integer monthToValidate = getInteger(requestParametersToValidate, eTypeParameter.MONTH);
			Long countryToValidate = getLong(requestParametersToValidate, eTypeParameter.COUNTRY);

			for (Request request : requests) {
				Set<RequestParameter> requestParameters = request.getRequestParameter();

				Long country = getLong(requestParameters, eTypeParameter.COUNTRY);
				if(country.equals(countryToValidate)){
					Integer year = getInteger(requestParameters,  eTypeParameter.YEAR);
					Integer month = getInteger(requestParameters, eTypeParameter.MONTH);
					if(Objects.equals(yearToValidate, year) && Objects.equals(monthToValidate, month) ) throw new RequestConflitParametersException();
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

    private Integer getInteger(final Set<RequestParameter> requestParameters, eTypeParameter typeParameter) throws ParameterNotExistException {
        String year = getParameter(requestParameters, typeParameter);
        return Integer.parseInt(year);
    }

    private Long getLong(final Set<RequestParameter> requestParameters, eTypeParameter typeParameter) throws ParameterNotExistException {
        String country = getParameter(requestParameters, typeParameter);
        return Long.parseLong(country);
    }

}
