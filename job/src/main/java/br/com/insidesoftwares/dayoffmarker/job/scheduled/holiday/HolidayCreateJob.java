package br.com.insidesoftwares.dayoffmarker.job.scheduled.holiday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayCreateJob {

	private final HolidayJobServiceBean holidayJobServiceBean;

	@Scheduled(
			cron = "${application.scheduling.holiday.create.cron:0 0 */1 * * *}"
	)
	public void schedulingRunRequestForCreateDays() {
		log.info("Starting the create of holidays");
		holidayJobServiceBean.createHolidayFromFixedHoliday();
		log.info("Finalizing the create of holidays");
	}

	@Scheduled(
			cron = "${application.scheduling.holiday.batch.create.cron:0 10 */1 * * *}"
	)
	public void schedulingRunBath() {
		log.info("Starting the update holiday request run batch");
		holidayJobServiceBean.schedulingRunBatch();
		log.info("Finalizing the update holiday request run batch");

	}

}
