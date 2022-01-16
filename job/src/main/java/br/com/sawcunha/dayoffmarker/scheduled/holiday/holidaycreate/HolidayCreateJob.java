package br.com.sawcunha.dayoffmarker.scheduled.holiday.holidaycreate;

import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.scheduled.holiday.HolidayJobServiceBean;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class HolidayCreateJob {

	private final HolidayJobServiceBean holidayJobServiceBean;
	private final LogService<HolidayCreateJob> logService;

	@PostConstruct
	public void init(){
		logService.init(HolidayCreateJob.class);
		logService.logInfor("Init HolidayCreateJob");
	}

	@Scheduled(
			fixedRateString = "${application.scheduling.holiday.create.fixedRateInMilliseconds}",
			initialDelayString = "${application.scheduling.holiday.initialDelayInMilliseconds}"
	)
	public void schedulingRunRequestForCreatingDays() {
		logService.logInfor("Starting the creation of holidays");
		holidayJobServiceBean.createHolidayFromFixedHoliday();
		logService.logInfor("Finalizing the creation of holidays");
	}

}
