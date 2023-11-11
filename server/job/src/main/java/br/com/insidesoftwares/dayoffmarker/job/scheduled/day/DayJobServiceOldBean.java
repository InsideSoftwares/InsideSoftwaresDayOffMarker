package br.com.insidesoftwares.dayoffmarker.job.scheduled.day;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestService;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class DayJobServiceOldBean {

    private final RequestService requestService;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @Autowired
    @Qualifier("jobCreatesDays")
    private Job jobCreatesDays;

    @Autowired
    @Qualifier("jobClearDayBatch")
    private Job jobClearDayBatch;

    public void schedulingRunBatch() {
        try {
            if (requestService.existRequestByTypeAndStatusRequest(TypeRequest.CREATE_DATE, StatusRequest.CREATED)) {
                JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
                        .getNextJobParameters(jobCreatesDays)
                        .toJobParameters();

                jobLauncher.run(jobCreatesDays, jobParameters);
                log.info("Starting batch for day creation.");
            }
        } catch (Exception e) {
            log.error("Error starting batch for day creation.", e);
        }
    }

    public void runClearDayBatch() {
        try {
            JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
                    .getNextJobParameters(jobClearDayBatch)
                    .toJobParameters();

            jobLauncher.run(jobClearDayBatch, jobParameters);
            log.info("Starting batch for day creation.");
        } catch (Exception e) {
            log.error("Error starting batch for day creation.", e);
        }
    }
}
