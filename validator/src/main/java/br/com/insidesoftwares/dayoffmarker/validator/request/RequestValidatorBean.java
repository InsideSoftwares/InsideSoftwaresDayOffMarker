package br.com.insidesoftwares.dayoffmarker.validator.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.repository.RequestRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class RequestValidatorBean implements RequestValidator {

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

    @Override
    public void validRequestInitial(final Set<RequestParameter> requestParametersToValidate) {
        try {
            List<Request> requests =
                    requestRepository.findAllRequestByTypeRequestAndNotStatusRequest(
                            TypeRequest.REQUEST_CREATION_DATE,
                            StatusRequest.ERROR
                    );

            Integer startYearToValidate = getInteger(requestParametersToValidate, TypeParameter.START_YEAR);
            Integer endYearToValidate = getInteger(requestParametersToValidate, TypeParameter.END_YEAR);

            for (Request request : requests) {
                Set<RequestParameter> requestParameters = request.getRequestParameter();

				Integer startYear = getInteger(requestParameters,  TypeParameter.START_YEAR);
				Integer endYear = getInteger(requestParameters, TypeParameter.END_YEAR);
				if(startYearToValidate >= startYear && startYearToValidate <= endYear ) throw new RequestConflictParametersException();
				if(endYearToValidate >= startYear && endYearToValidate <= endYear ) throw new RequestConflictParametersException();
            }
        } catch (ParameterNotExistException | RequestConflictParametersException parameterNotExistExeption){
            logService.logError(
                    parameterNotExistExeption.getCode(),
                    parameterNotExistExeption.getMessage(),
                    parameterNotExistExeption.getCause()
            );
            throw parameterNotExistExeption;
        } catch (Exception e){
            logService.logError(ExceptionCodeError.GENERIC.getCode(), e.getMessage(), e.getCause());
            throw e;
        }
    }

	@Override
	public void validRequestCreateDate(final Set<RequestParameter> requestParametersToValidate) {
		try {
			List<Request> requests =
					requestRepository.findAllRequestByTypeRequestAndNotStatusRequest(
							TypeRequest.CREATE_DATE,
							StatusRequest.ERROR
					);

			Integer yearToValidate = getInteger(requestParametersToValidate, TypeParameter.YEAR);
			Integer monthToValidate = getInteger(requestParametersToValidate, TypeParameter.MONTH);

			for (Request request : requests) {
				Set<RequestParameter> requestParameters = request.getRequestParameter();

				Integer year = getInteger(requestParameters,  TypeParameter.YEAR);
				Integer month = getInteger(requestParameters, TypeParameter.MONTH);
				if(Objects.equals(yearToValidate, year) && Objects.equals(monthToValidate, month) ) throw new RequestConflictParametersException();
			}
		} catch (ParameterNotExistException | RequestConflictParametersException parameterNotExistExeption){
			logService.logError(
					parameterNotExistExeption.getCode(),
					parameterNotExistExeption.getMessage(),
					parameterNotExistExeption.getCause()
			);
			throw parameterNotExistExeption;
		} catch (Exception e){
			logService.logError(ExceptionCodeError.GENERIC.getCode(), e.getMessage(), e.getCause());
			throw e;
		}
	}

	@Override
	public void validRequestUpdateHoliday(final Set<RequestParameter> requestParametersToValidate)  {
		try {
			List<Request> requests =
					requestRepository.findAllRequestByTypeRequestAndStatusRequest(
							TypeRequest.UPDATE_HOLIDAY,
							List.of(
									StatusRequest.CREATED,
									StatusRequest.WAITING,
									StatusRequest.RUNNING
							)
					);

			Long fixedHolidayIDToValidate = getLong(requestParametersToValidate, TypeParameter.FIXED_HOLIDAY_ID);

			for (Request request : requests) {
				Set<RequestParameter> requestParameters = request.getRequestParameter();
				Long fixedHolidayID = getLong(requestParameters, TypeParameter.FIXED_HOLIDAY_ID);
				if(fixedHolidayID.equals(fixedHolidayIDToValidate)){
					throw new RequestConflictParametersException();
				}
			}
		} catch (ParameterNotExistException | RequestConflictParametersException parameterNotExistExeption){
			logService.logError(
					parameterNotExistExeption.getCode(),
					parameterNotExistExeption.getMessage(),
					parameterNotExistExeption.getCause()
			);
			throw parameterNotExistExeption;
		} catch (Exception e){
			logService.logError(ExceptionCodeError.GENERIC.getCode(), e.getMessage(), e.getCause());
			throw e;
		}
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

    private Integer getInteger(final Set<RequestParameter> requestParameters, TypeParameter typeParameter) throws ParameterNotExistException {
        String year = getParameter(requestParameters, typeParameter);
        return Integer.parseInt(year);
    }

    private Long getLong(final Set<RequestParameter> requestParameters, TypeParameter typeParameter) throws ParameterNotExistException {
        String country = getParameter(requestParameters, typeParameter);
        return Long.parseLong(country);
    }

}
