package br.com.sawcunha.dayoffmarker.scheduled.holiday;

import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
			cron = "${application.scheduling.holiday.update.cron}"
	)
	public void schedulingRunRequestForUpdateDays() {
		logService.logInfor("Starting the update of holidays");
		holidayJobServiceBean.updateHolidayFromFixedHoliday();
		logService.logInfor("Finalizing the update of holidays");
	}

	@Scheduled(
			cron = "${application.scheduling.holiday.batch.cron}"
	)
	public void schedulingRunBath() {
		logService.logInfor("Starting the update holiday request run batch");
		holidayJobServiceBean.schedulingRunBatch();
		logService.logInfor("Finalizing the update holiday request run batch");

	}

}
