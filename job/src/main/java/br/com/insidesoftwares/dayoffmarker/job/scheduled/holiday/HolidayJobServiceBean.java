package br.com.insidesoftwares.dayoffmarker.job.scheduled.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestService;
import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
	private final RequestService requestService;
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
			if(
					dayService.ownsDays() &&
					!requestService.existRequestByTypeAndStatusRequest(TypeRequest.UPDATE_HOLIDAY, StatusRequest.CREATED)
			) {

				logService.logInfor("Update the Holidays.");
				List<FixedHoliday> fixedHolidays = fixedHolidayService.findAll();

				fixedHolidays.forEach(fixedHoliday -> {
					try {
						Request newRequest = createRequest(fixedHoliday.getId());
						requestService.saveRequest(newRequest);
					} catch (Exception e) {
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
			if(requestService.existRequestByTypeAndStatusRequest(TypeRequest.UPDATE_HOLIDAY, StatusRequest.CREATED)) {
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
	) {
		UUID keyRequest = UUID.randomUUID();

		Request newRequest =  Request.builder()
				.id(keyRequest)
				.statusRequest(StatusRequest.CREATED)
				.typeRequest(TypeRequest.UPDATE_HOLIDAY)
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
	) {

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
				RequestParameter.builder()
						.typeParameter(TypeParameter.FIXED_HOLIDAY_ID)
						.typeValue(TypeValue.LONG)
						.value(fixedHolidayID)
						.request(request)
						.build()
		);

		requestValidator.validRequestUpdateHoliday(requestParameters);

		return requestParameters;
	}
}
