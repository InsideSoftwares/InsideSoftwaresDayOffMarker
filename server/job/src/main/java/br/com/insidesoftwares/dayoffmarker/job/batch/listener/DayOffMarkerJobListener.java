package br.com.insidesoftwares.dayoffmarker.job.batch.listener;

import br.com.insidesoftwares.dayoffmarker.job.scheduled.cache.CacheJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;

@Slf4j
@RequiredArgsConstructor
public class DayOffMarkerJobListener extends CompositeJobExecutionListener {

	private final CacheJob cacheJob;

	@Override
	public void beforeJob(final JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		log.info("Initialized step {}", jobExecution.getJobInstance().getJobName());
		log.info("Start Time: {}", jobExecution.getStartTime());
		log.info("Status {}", jobExecution.getStatus());
	}

	@Override
    public void afterJob(final JobExecution jobExecution) {
        super.afterJob(jobExecution);
		log.info("Last Updated step {}", jobExecution.getLastUpdated());
		log.info("Status {}", jobExecution.getStatus());
		log.info("End Time: {}", jobExecution.getEndTime());
		log.info("Finalized step {}", jobExecution.getJobInstance().getJobName());
		cacheJob.clearCacheScheduled();
    }
}
