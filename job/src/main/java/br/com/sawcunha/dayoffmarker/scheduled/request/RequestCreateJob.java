package br.com.sawcunha.dayoffmarker.scheduled.request;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@RequiredArgsConstructor
public class RequestCreateJob {

    @Async
    @Scheduled(
            fixedRateString = "${application.scheduling.request.create_date.fixedRateInMilliseconds}",
            initialDelayString = "${application.scheduling.request.create_date.initialDelayInMilliseconds}"
    )
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println(
                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }

}
