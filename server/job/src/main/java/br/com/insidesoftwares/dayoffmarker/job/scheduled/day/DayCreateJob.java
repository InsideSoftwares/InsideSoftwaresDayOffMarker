package br.com.insidesoftwares.dayoffmarker.job.scheduled.day;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DayCreateJob {

    private final DayJobServiceBean dayJobServiceBean;

    @Async
    @Scheduled(
            cron = "${application.scheduling.day.create_date.find_request_create_date:0 0 */1 * * *}"
    )
    public void schedulingFindRequestForCreatingDays() {
        log.info("Starting request query to create the days.");
        dayJobServiceBean.findRequestForCreatingDays();
        log.info("Finalizing request query to create the days.");
    }

    @Scheduled(
            cron = "${application.scheduling.day.create_date.run_request_create_date:0 0 */3 * * *}"
    )
    public void schedulingRunRequestForCreatingDays() {
        log.info("Starting the day creation request run");
        dayJobServiceBean.runRequestForCreatingDays();
        log.info("Finalizing the day creation request run");
    }

    @Scheduled(
            cron = "${application.scheduling.day.create_date.run_create_day_batch:0 0 */5 * * *}"
    )
    public void schedulingRunBath() {
        log.info("Starting the day creation request run batch");
        dayJobServiceBean.schedulingRunBatch();
        log.info("Finalizing the day creation request run batch");

    }

    @Async
    @Scheduled(
            cron = "${application.scheduling.day.create_date.clear_day_batch: 0 0 */12 * * *}"
    )
    public void schedulingClearDayBatch() {
        log.info("Starting request query to create the days.");
        dayJobServiceBean.runClearDayBatch();
        log.info("Finalizing request query to create the days.");
    }

}
