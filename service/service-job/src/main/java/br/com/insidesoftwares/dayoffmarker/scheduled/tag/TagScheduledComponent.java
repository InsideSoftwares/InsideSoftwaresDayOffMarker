package br.com.insidesoftwares.dayoffmarker.scheduled.tag;

import br.com.insidesoftwares.dayoffmarker.specification.job.tag.TagJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagScheduledComponent {

    private final TagJobService tagJobService;

    @Scheduled(fixedDelay = 5, initialDelay = 2, timeUnit = TimeUnit.MINUTES)
    public void processLinkUnlinkTagDayBatch() {
        log.info("Starting request query to link and unlink Tag.");
        tagJobService.processLinkUnlinkTagDayBatch();
        log.info("Finalizing request query to link and unlink Tag.");
    }

}
