package br.com.insidesoftwares.dayoffmarker.job.scheduled.day;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestService;
import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.job.scheduled.utils.RequestUtils;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
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

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DayJobServiceBean {

	private final RequestService requestService;
	private final RequestValidator requestValidator;
	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;
	private final LogService<DayJobServiceBean> logService;

	@Autowired
	@Qualifier("jobCreatesDays")
	private Job jobCreatesDays;

	@Autowired
	@Qualifier("jobClearDayBatch")
	private Job jobClearDayBatch;

	@PostConstruct
	public void init(){
		logService.init(DayJobServiceBean.class);
		logService.logInfor("Init RequestJobServiceBean");
	}

	@Transactional(rollbackFor = {
			Exception.class
	})
	public void findRequestForCreatingDays() {

		List<Request> requests = requestService
				.findAllRequestByTypeAndStatus(TypeRequest.REQUEST_CREATION_DATE, StatusRequest.CREATED);

		requests.forEach(request -> {
			request.setStatusRequest(StatusRequest.WAITING);
			requestService.saveAndFlushRequest(request);
		});
		logService.logInfor("Updating request to be status WAITING.");
	}

	@Transactional(rollbackFor = {
			ParameterNotExistException.class,
			RequestConflictParametersException.class,
			Exception.class
	})
	public void runRequestForCreatingDays() {
		List<Request> requests = requestService
				.findAllRequestByTypeAndStatus(TypeRequest.REQUEST_CREATION_DATE, StatusRequest.WAITING);

		requests.forEach(request -> {
			request.setStatusRequest(StatusRequest.RUNNING);
			request.setStartDate(LocalDateTime.now());
			requestService.saveAndFlushRequest(request);
		});
		logService.logInfor("Updating request to be status RUNNING.");

		requests.forEach(request -> {
			try{
				createsRequestWithMonths(request);
				request.setStatusRequest(StatusRequest.SUCCESS);
				request.setEndDate(LocalDateTime.now());
			}catch (Exception e){
				logService.logError(e.getMessage(),e);
				request.setStatusRequest(StatusRequest.ERROR);
				request.setEndDate(LocalDateTime.now());
			} finally {
				requestService.saveAndFlushRequest(request);
			}
		});
	}

	public void schedulingRunBatch(){
		try {
			if(requestService.existRequestByTypeAndStatusRequest(TypeRequest.CREATE_DATE, StatusRequest.CREATED)) {
				JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
						.getNextJobParameters(jobCreatesDays)
						.toJobParameters();

				jobLauncher.run(jobCreatesDays, jobParameters);
				logService.logInfor("Starting batch for day creation.");
			}
		}catch (Exception e){
			logService.logError("Error starting batch for day creation.", e);
		}
	}

	@Transactional(rollbackFor = {
			ParameterNotExistException.class,
			RequestConflictParametersException.class,
			Exception.class
	})
	public void createsRequestWithMonths(final Request request) {
		logService.logInfor("Starting the creation of the requests to create the Days.");

		Integer startYear = RequestUtils.getStartYear(request.getRequestParameter());
		Integer endYear = RequestUtils.getEndYear(request.getRequestParameter());

		while (startYear <= endYear) {

			int month = 1;

			while (month <= 12){
				Request newRequest = createRequest(request, month, startYear);
				requestService.saveRequest(newRequest);
				month++;
			}
			startYear++;
		}
		logService.logInfor("Finalizing the creation of requests to create days.");
	}

	private Request createRequest(
			final Request request,
			final Integer month,
			final Integer year
	)  {
		UUID keyRequest = UUID.randomUUID();

		Request newRequest =  Request.builder()
				.id(keyRequest)
				.statusRequest(StatusRequest.CREATED)
				.typeRequest(TypeRequest.CREATE_DATE)
				.createDate(LocalDateTime.now())
				.requesting(request.getRequesting())
				.build();

		Set<RequestParameter> requestParameters =
				createRequestParameter(
						newRequest,
						month.toString(),
						year.toString(),
						request.getId().toString()
				);

		newRequest.setRequestParameter(requestParameters);

		return newRequest;
	}

	private Set<RequestParameter> createRequestParameter(
			final Request request,
			final String month,
			final String year,
			final String requestID
	)  {

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
				RequestUtils.createRequestParameter(request, TypeParameter.MONTH, TypeValue.INT, month)
		);
		requestParameters.add(
				RequestUtils.createRequestParameter(request, TypeParameter.YEAR, TypeValue.INT, year)
		);
		requestParameters.add(
				RequestUtils.createRequestParameter(request, TypeParameter.REQUEST_ORIGINAL, TypeValue.STRING, requestID)
		);

		requestValidator.validRequestCreateDate(requestParameters);

		return requestParameters;
	}

	public void runClearDayBatch() {
		try {
			JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
				.getNextJobParameters(jobClearDayBatch)
				.toJobParameters();

			jobLauncher.run(jobClearDayBatch, jobParameters);
			logService.logInfor("Starting batch for day creation.");
		}catch (Exception e){
			logService.logError("Error starting batch for day creation.", e);
		}
	}
}
