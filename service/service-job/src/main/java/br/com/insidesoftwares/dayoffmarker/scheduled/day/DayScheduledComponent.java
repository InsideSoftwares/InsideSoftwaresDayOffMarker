package br.com.insidesoftwares.dayoffmarker.scheduled.day;

import br.com.insidesoftwares.dayoffmarker.specification.job.day.DayJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class DayScheduledComponent {

    private final DayJobService dayJobService;

    @Scheduled(fixedDelay = 15, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void schedulingFindRequestForCreatingDays() {
        log.info("Starting request query to create the days.");
        dayJobService.processCreationDayBatch();
        log.info("Finalizing request query to create the days.");
    }

}
