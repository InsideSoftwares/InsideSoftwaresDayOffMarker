package br.com.insidesoftwares.dayoffmarker.job.scheduled.tag;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestService;
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
public class TagJobServiceBean {

	private final RequestService requestService;
	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;

	@Autowired
	@Qualifier("jobLinkTagInDay")
	private Job jobLinkTagInDay;

	@Autowired
	@Qualifier("jobUnlinkTagInDay")
	private Job jobUnlinkTagInDay;

	public void schedulingRunBatchLinkTag(){
		try {
			if(requestService.existRequestByTypeAndStatusRequest(TypeRequest.LINK_TAG, StatusRequest.CREATED)) {
				JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
						.getNextJobParameters(jobLinkTagInDay)
						.toJobParameters();

				jobLauncher.run(jobLinkTagInDay, jobParameters);
				log.info("Starting batch for link tag.");
			}
		}catch (Exception e){
			log.error("Error starting batch for link tag.", e);
		}
	}

	public void schedulingRunBatchUnlinkTag(){
		try {
			if(requestService.existRequestByTypeAndStatusRequest(TypeRequest.UNLINK_TAG, StatusRequest.CREATED)) {
				JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
					.getNextJobParameters(jobUnlinkTagInDay)
					.toJobParameters();

				jobLauncher.run(jobUnlinkTagInDay, jobParameters);
				log.info("Starting batch for unlink tag.");
			}
		}catch (Exception e){
			log.error("Error starting batch for unlink tag.", e);
		}
	}
}
