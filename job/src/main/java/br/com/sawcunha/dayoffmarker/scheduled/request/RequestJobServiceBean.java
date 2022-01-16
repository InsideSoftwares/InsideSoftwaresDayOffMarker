package br.com.sawcunha.dayoffmarker.scheduled.request;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
import br.com.sawcunha.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.request.RequestConflitParametersException;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.entity.RequestParameter;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestJobServiceBean {

	private final RequestRepository requestRepository;
	private final RequestValidator requestValidator;
	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;
	private final LogService<RequestJobServiceBean> logService;

	@Autowired
	@Qualifier("jobCreatesDays")
	private Job jobCreatesDays;

	@PostConstruct
	public void init(){
		logService.init(RequestJobServiceBean.class);
		logService.logInfor("Init RequestJobServiceBean");
	}

	@Transactional(rollbackFor = {
			Exception.class
	})
	public void findRequestForCreatingDays() {

		List<Request> requests = requestRepository
				.findAllRequestByTypeRequestAndStatusRequest(eTypeRequest.REQUEST_CREATION_DATE, eStatusRequest.CREATED);

		requests.forEach(request -> {
			request.setStatusRequest(eStatusRequest.WAITING);
			requestRepository.saveAndFlush(request);
		});
		logService.logInfor("Updating request to be status WAITING.");
	}

	@Transactional(rollbackFor = {
			ParameterNotExistException.class,
			RequestConflitParametersException.class,
			Exception.class
	})
	public void runRequestForCreatingDays() {
		List<Request> requests = requestRepository
				.findAllRequestByTypeRequestAndStatusRequest(eTypeRequest.REQUEST_CREATION_DATE, eStatusRequest.WAITING);

		requests.forEach(request -> {
			request.setStatusRequest(eStatusRequest.RUNNING);
			request.setStartDate(LocalDateTime.now());
			requestRepository.saveAndFlush(request);
		});
		logService.logInfor("Updating request to be status RUNNING.");

		requests.forEach(request -> {
			try{
				createsRequestWithMonths(request);
				request.setStatusRequest(eStatusRequest.SUCCESS);
				request.setEndDate(LocalDateTime.now());
			}catch (Exception e){
				logService.logError(e.getMessage(),e);
				request.setStatusRequest(eStatusRequest.ERROR);
				request.setEndDate(LocalDateTime.now());
			} finally {
				requestRepository.saveAndFlush(request);
			}
		});
	}

	public void schedulingRunBatch(){
		try {
			if(requestRepository.existRequestByStatusRequest(eStatusRequest.CREATED, eTypeRequest.CREATE_DATE)) {
				JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
						.getNextJobParameters(jobCreatesDays)
						.toJobParameters();

				jobLauncher.run(jobCreatesDays, jobParameters);
				logService.logError("Starting batch for day creation.");
			}
		}catch (Exception e){
			logService.logError("Error starting batch for day creation.", e);
		}
	}

	@Transactional(rollbackFor = {
			ParameterNotExistException.class,
			RequestConflitParametersException.class,
			Exception.class
	})
	public void createsRequestWithMonths(final Request request) throws Exception {
		logService.logInfor("Starting the creation of the requests to create the Days.");

		Long countryID = getCountry(request.getRequestParameter());
		Integer startYear = getStartYear(request.getRequestParameter());
		Integer endYear = getEndYear(request.getRequestParameter());

		while (startYear <= endYear) {

			int month = 1;

			while (month <= 12){
				Request newRequest = createRequest(request, month, startYear, countryID);
				requestRepository.save(newRequest);
				month++;
			}
			startYear++;
		}
		logService.logInfor("Finalizing the creation of requests to create days.");
	}

	private Request createRequest(
			final Request request,
			final Integer month,
			final Integer year,
			final Long countryID
	) throws Exception {
		UUID keyRequest = UUID.randomUUID();

		Request newRequest =  Request.builder()
				.id(keyRequest)
				.statusRequest(eStatusRequest.CREATED)
				.typeRequest(eTypeRequest.CREATE_DATE)
				.createDate(LocalDateTime.now())
				.requesting(request.getRequesting())
				.build();

		Set<RequestParameter> requestParameters =
				createRequestParameter(
						newRequest,
						countryID.toString(),
						month.toString(),
						year.toString());

		newRequest.setRequestParameter(requestParameters);

		return newRequest;
	}

	private Set<RequestParameter> createRequestParameter(
			final Request request,
			final String countryID,
			final String month,
			final String year
	) throws Exception {

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
				createRequestParameter(request, eTypeParameter.COUNTRY, eTypeValue.LONG, countryID)
		);
		requestParameters.add(
				createRequestParameter(request, eTypeParameter.MONTH, eTypeValue.INT, month)
		);
		requestParameters.add(
				createRequestParameter(request, eTypeParameter.YEAR, eTypeValue.INT, year)
		);

		requestValidator.validRequestCreateDate(requestParameters);

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
