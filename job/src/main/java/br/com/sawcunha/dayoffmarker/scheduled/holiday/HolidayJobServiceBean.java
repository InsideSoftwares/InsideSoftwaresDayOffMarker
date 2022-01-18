package br.com.sawcunha.dayoffmarker.scheduled.holiday;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.entity.RequestParameter;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import br.com.sawcunha.dayoffmarker.specification.service.FixedHolidayService;
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

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HolidayJobServiceBean {

	private final DayService dayService;
	private final FixedHolidayService fixedHolidayService;
	private final LogService<HolidayJobServiceBean> logService;
	private final RequestRepository requestRepository;
	private final RequestValidator requestValidator;
	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;

	@Autowired
	@Qualifier("jobUpdateHoliday")
	private Job jobUpdateHoliday;

	private static final String USER_REQUESTING = "System";

	@PostConstruct
	public void init(){
		logService.init(HolidayJobServiceBean.class);
		logService.logInfor("Init HolidayJobServiceBean");
	}

	public void updateHolidayFromFixedHoliday(){
		try {
			logService.logInfor("Starting the update of holidays.");
			if(dayService.ownsDays()) {
				logService.logInfor("Update the Holidays.");
				List<FixedHoliday> fixedHolidays = fixedHolidayService.findAllByCountry();

				fixedHolidays.forEach(fixedHoliday -> {
					try {
						Request newRequest = createRequest(fixedHoliday.getId());
						requestRepository.save(newRequest);
					}catch (Exception e){
						logService.logError("It was not possible to update the Holidays.", e);
					} finally {
						logService.logInfor("Finishing the update of Holidays.");
					}
				});
			}

		}catch (Exception e){
			logService.logError("It was not possible to update the Holidays.", e);
		} finally {
			logService.logInfor("Finishing the update of Holidays.");
		}
	}

	public void schedulingRunBatch(){
		try {
			if(requestRepository.existRequestByStatusRequest(eStatusRequest.CREATED, eTypeRequest.UPDATE_HOLIDAY)) {
				JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
						.getNextJobParameters(jobUpdateHoliday)
						.toJobParameters();

				jobLauncher.run(jobUpdateHoliday, jobParameters);
				logService.logError("Starting batch for update holiday.");
			}
		}catch (Exception e){
			logService.logError("Error starting batch for update holiday.", e);
		}
	}

	private Request createRequest(
			final Long fixedHolidayID
	) throws Exception {
		UUID keyRequest = UUID.randomUUID();

		Request newRequest =  Request.builder()
				.id(keyRequest)
				.statusRequest(eStatusRequest.CREATED)
				.typeRequest(eTypeRequest.UPDATE_HOLIDAY)
				.createDate(LocalDateTime.now())
				.requesting(USER_REQUESTING)
				.build();

		Set<RequestParameter> requestParameters =
				createRequestParameter(
						newRequest,
						fixedHolidayID.toString()
				);

		newRequest.setRequestParameter(requestParameters);

		return newRequest;
	}

	private Set<RequestParameter> createRequestParameter(
			final Request request,
			final String fixedHolidayID
	) throws Exception {

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
				RequestParameter.builder()
						.typeParameter(eTypeParameter.FIXED_HOLIDAY_ID)
						.typeValue(eTypeValue.LONG)
						.value(fixedHolidayID)
						.request(request)
						.build()
		);

		requestValidator.validRequestUpdateHoliday(requestParameters);

		return requestParameters;
	}
}
