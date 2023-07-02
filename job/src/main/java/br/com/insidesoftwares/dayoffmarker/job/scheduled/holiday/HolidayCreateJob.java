package br.com.insidesoftwares.dayoffmarker.job.scheduled.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class HolidayCreateJob {

	private final HolidayJobServiceBean holidayJobServiceBean;
	private final LogService<HolidayCreateJob> logService;

	@PostConstruct
	public void init(){
		logService.init(HolidayCreateJob.class);
		logService.logInfor("Init HolidayCreateJob");
	}

	@Scheduled(
			cron = "${application.scheduling.holiday.create.cron:0 0 */1 * * *}"
	)
	public void schedulingRunRequestForCreateDays() {
		logService.logInfor("Starting the create of holidays");
		holidayJobServiceBean.createHolidayFromFixedHoliday();
		logService.logInfor("Finalizing the create of holidays");
	}

	@Scheduled(
			cron = "${application.scheduling.holiday.batch.create.cron:0 10 */1 * * *}"
	)
	public void schedulingRunBath() {
		logService.logInfor("Starting the update holiday request run batch");
		holidayJobServiceBean.schedulingRunBatch();
		logService.logInfor("Finalizing the update holiday request run batch");

	}

}
