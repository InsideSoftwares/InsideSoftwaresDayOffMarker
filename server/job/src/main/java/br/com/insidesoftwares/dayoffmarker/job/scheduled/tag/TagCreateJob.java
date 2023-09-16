package br.com.insidesoftwares.dayoffmarker.job.scheduled.tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagCreateJob {

	private final TagJobServiceBean tagJobServiceBean;

	@Scheduled(
		cron = "${application.scheduling.tag.link.run_batch:0 0 */1 * * *}"
	)
	public void schedulingRunBatchLinkTag() {
		log.info("Starting the link tag in day request run batch");
		tagJobServiceBean.schedulingRunBatchLinkTag();
		log.info("Finalizing the link tag in day request run batch");

	}

	@Scheduled(
		cron = "${application.scheduling.tag.unlink.run_batch:0 0 */1 * * *}"
	)
	public void schedulingRunBatchUnlinkTag() {
		log.info("Starting the unlink tag in day request run batch");
		tagJobServiceBean.schedulingRunBatchUnlinkTag();
		log.info("Finalizing the unlink tag in day request run batch");

	}

}
