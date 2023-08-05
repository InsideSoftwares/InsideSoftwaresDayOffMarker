package br.com.insidesoftwares.dayoffmarker.service.listener;

import br.com.insidesoftwares.commons.specification.InsideSoftwaresStartupListener;
import br.com.insidesoftwares.dayoffmarker.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DayOffMarkerStartupListener implements InsideSoftwaresStartupListener {

	private final RequestCreationService requestCreationService;
	private final DayRepository dayRepository;

	@Override
	public void onStartupSystem(ApplicationReadyEvent event) {
		createDays();
	}

	private void createDays() {
		log.info("Init create days");
		if(validateHasDays()) {
			log.info("Has configured days");
			return;
		}

		try {
			String keyRequest = requestCreationService.createInitialApplication();

			log.info(String.format("Registered request to create the days: %s", keyRequest));
		} catch (Exception exception) {
			log.error("An error occurred when trying to create the request to create the days", exception);
		}
	}

	private boolean validateHasDays() {
		log.info("Validate has days");
		return dayRepository.existsDay(1);
	}

}
