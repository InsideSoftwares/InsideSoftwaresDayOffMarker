package br.com.sawcunha.dayoffmarker.scheduled.request.requestcreate;

import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.scheduled.request.RequestJobServiceBean;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@EnableAsync
@RequiredArgsConstructor
public class RequestCreateJob {

	private final LogService<RequestCreateJob> logService;
	private final RequestJobServiceBean requestJobServiceBean;

	@PostConstruct
	public void init(){
		logService.init(RequestCreateJob.class);
		logService.logInfor("Init RequestCreateJob");
	}

	@Async
    @Scheduled(
            fixedRateString = "${application.scheduling.request.create_date.find_request_create_date.fixedRateInMilliseconds}",
            initialDelayString = "${application.scheduling.request.create_date.initialDelayInMilliseconds}"
    )
    public void schedulingFindRequestForCreatingDays() {
		logService.logInfor("Starting request query to create the days.");
		requestJobServiceBean.findRequestForCreatingDays();
		logService.logInfor("Finalizing request query to create the days.");
    }

	@Scheduled(
			fixedRateString = "${application.scheduling.request.create_date.run_request_create_date.fixedRateInMilliseconds}",
			initialDelayString = "${application.scheduling.request.create_date.initialDelayInMilliseconds}"
	)
	public void schedulingRunRequestForCreatingDays() {
		logService.logInfor("Starting the day creation request run");
		requestJobServiceBean.runRequestForCreatingDays();
		logService.logInfor("Finalizing the day creation request run");
	}

	@Scheduled(
			fixedRateString = "${application.scheduling.request.create_date.batch.fixedRateInMilliseconds}",
			initialDelayString = "${application.scheduling.request.create_date.initialDelayInMilliseconds}"
	)
	public void schedulingRunBath() {
		logService.logInfor("Starting the day creation request run batch");
		requestJobServiceBean.schedulingRunBatch();
		logService.logInfor("Finalizing the day creation request run batch");

	}


}
