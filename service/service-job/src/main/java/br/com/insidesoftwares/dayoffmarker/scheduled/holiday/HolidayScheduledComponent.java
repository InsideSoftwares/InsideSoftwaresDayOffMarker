package br.com.insidesoftwares.dayoffmarker.scheduled.holiday;

import br.com.insidesoftwares.dayoffmarker.job.holiday.FixedHolidayJobServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayScheduledComponent {

    private final FixedHolidayJobServiceBean fixedHolidayJobServiceBean;

    @Scheduled(fixedDelay = 5, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void schedulingFindRequestForUpdateFixedHoliday() {
        log.info("Starting request query to update fixed holidays.");
        fixedHolidayJobServiceBean.processFixedHolidayBatch();
        log.info("Finalizing request query to update fixed holidays.");
    }

}
