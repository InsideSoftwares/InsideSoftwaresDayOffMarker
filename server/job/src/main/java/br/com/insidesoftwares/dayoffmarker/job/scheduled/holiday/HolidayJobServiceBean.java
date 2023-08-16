package br.com.insidesoftwares.dayoffmarker.job.scheduled.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayJobServiceBean {

	private final DayService dayService;
	private final FixedHolidayService fixedHolidayService;
	private final RequestService requestService;
	private final RequestCreationService requestCreationService;
	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;

	@Autowired
	@Qualifier("jobCreateHoliday")
	private Job jobCreateHoliday;

	public void createHolidayFromFixedHoliday(){
		try {
			log.info("Starting the create of holidays.");
			if(
					dayService.ownsDays() &&
					!requestService.existRequestByTypeAndStatusRequest(TypeRequest.CREATE_HOLIDAY, StatusRequest.CREATED)
			) {

				log.info("Create the Holidays.");
				List<FixedHoliday> fixedHolidays = fixedHolidayService.findAllByEnable(true);

				fixedHolidays.forEach(fixedHoliday -> {
					try {
 						boolean isDayNotHoliday = dayService.isDaysWithoutHolidaysByByDayAndMonthAndFixedHolidayIDOrNotHoliday(
							fixedHoliday.getDay(),
							fixedHoliday.getMonth(),
							fixedHoliday.getId()
						);

						if(isDayNotHoliday){
							String requestId = requestCreationService.createHolidayRequest(fixedHoliday.getId());
							log.info("Request holiday created: {}", requestId);
						} else {
							log.info("No days without holidays");
						}
					} catch (Exception e) {
						log.error("It was not possible to create the Holidays.", e);
					} finally {
						log.info("Finishing the create of Holidays.");
					}
				});

			}

		}catch (Exception e){
			log.error("It was not possible to create the Holidays.", e);
		} finally {
			log.info("Finishing the create of Holidays.");
		}
	}

	public void schedulingRunBatch(){
		try {
			if(requestService.existRequestByTypeAndStatusRequest(TypeRequest.CREATE_HOLIDAY, StatusRequest.CREATED)) {
				JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
						.getNextJobParameters(jobCreateHoliday)
						.toJobParameters();

				jobLauncher.run(jobCreateHoliday, jobParameters);
				log.error("Starting batch for create holiday.");
			}
		}catch (Exception e){
			log.error("Error starting batch for create holiday.", e);
		}
	}
}
