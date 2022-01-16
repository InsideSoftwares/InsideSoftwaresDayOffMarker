package br.com.sawcunha.dayoffmarker.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

@Slf4j
public class JobCreateDaysListener extends JobExecutionListenerSupport {

	@Override
	public void beforeJob(final JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		log.info("Initialized step {}", jobExecution.getJobConfigurationName());
		log.info("Start Time: {}", jobExecution.getStartTime());
		log.info("Status {}", jobExecution.getStatus());
	}

	@Override
    public void afterJob(final JobExecution jobExecution) {
        super.afterJob(jobExecution);
		log.info("Last Updated step {}", jobExecution.getLastUpdated());
		log.info("Status {}", jobExecution.getStatus());
		log.info("End Time: {}", jobExecution.getEndTime());
		log.info("Finalized step {}", jobExecution.getJobConfigurationName());
    }
}
