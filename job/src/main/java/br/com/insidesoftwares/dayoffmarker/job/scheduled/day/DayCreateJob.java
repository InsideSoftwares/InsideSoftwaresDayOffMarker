package br.com.insidesoftwares.dayoffmarker.job.scheduled.day;

import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DayCreateJob {

	private final LogService<DayCreateJob> logService;
	private final DayJobServiceBean dayJobServiceBean;

	@PostConstruct
	public void init(){
		logService.init(DayCreateJob.class);
		logService.logInfor("Init RequestCreateJob");
	}

	@Async
    @Scheduled(
		cron = "${application.scheduling.day.create_date.find_request_create_date:0 0 */1 * * *}"
    )
    public void schedulingFindRequestForCreatingDays() {
		logService.logInfor("Starting request query to create the days.");
		dayJobServiceBean.findRequestForCreatingDays();
		logService.logInfor("Finalizing request query to create the days.");
    }

	@Scheduled(
		cron = "${application.scheduling.day.create_date.run_request_create_date:0 0 */3 * * *}"
	)
	public void schedulingRunRequestForCreatingDays() {
		logService.logInfor("Starting the day creation request run");
		dayJobServiceBean.runRequestForCreatingDays();
		logService.logInfor("Finalizing the day creation request run");
	}

	@Scheduled(
		cron = "${application.scheduling.day.create_date.run_create_day_batch:0 0 */5 * * *}"
	)
	public void schedulingRunBath() {
		logService.logInfor("Starting the day creation request run batch");
		dayJobServiceBean.schedulingRunBatch();
		logService.logInfor("Finalizing the day creation request run batch");

	}

	@Async
	@Scheduled(
		cron = "${application.scheduling.day.create_date.clear_day_batch: 0 0 */12 * * *}"
	)
	public void schedulingClearDayBatch() {
		logService.logInfor("Starting request query to create the days.");
		dayJobServiceBean.runClearDayBatch();
		logService.logInfor("Finalizing request query to create the days.");
	}

}
