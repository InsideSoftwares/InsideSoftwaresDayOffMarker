package br.com.insidesoftwares.dayoffmarker.service.listener;

import br.com.insidesoftwares.commons.specification.InsideSoftwaresStartupListener;
import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.RequestRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class DayOffMarkerStartupListener implements InsideSoftwaresStartupListener {

	private final DayRepository dayRepository;
	private final ConfigurationService configurationService;
	private final RequestValidator requestValidator;
	private final RequestRepository requestRepository;

	private final static String REQUESTING = "System";

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
			UUID keyRequest = UUID.randomUUID();
			Request request = Request.builder()
				.id(keyRequest)
				.createDate(LocalDateTime.now())
				.typeRequest(TypeRequest.REQUEST_CREATION_DATE)
				.statusRequest(StatusRequest.CREATED)
				.requesting(REQUESTING)
				.build();

			request.setRequestParameter(
				createRequestParameterInitial(request)
			);

			requestRepository.save(request);

			log.info(String.format("Registered request to create the days: %s", keyRequest));
		} catch (Exception exception) {
			log.error("An error occurred when trying to create the request to create the days", exception);
		}
	}

	private boolean validateHasDays() {
		log.info("Validate has days");
		return dayRepository.existsDay(1);
	}

	private Set<RequestParameter> createRequestParameterInitial(final Request request) {
		String limitBackYears = configurationService.findValueConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS);
		String limitForwardYears = configurationService.findValueConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS);
		LocalDate baseDate = LocalDate.now();

		String startYear = DateUtils.returnYearMinus(baseDate,limitBackYears);
		String endYear = DateUtils.returnYearPlus(baseDate,limitForwardYears);

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
			createRequestParameter(request, TypeParameter.START_YEAR, TypeValue.INT, startYear)
		);
		requestParameters.add(
			createRequestParameter(request, TypeParameter.END_YEAR, TypeValue.INT, endYear)
		);

		requestValidator.validRequestInitial(requestParameters);

		return requestParameters;
	}

	private RequestParameter createRequestParameter(
		final Request request,
		final TypeParameter typeParameter,
		final TypeValue typeValue,
		final String value
	){
		return RequestParameter.builder()
			.typeParameter(typeParameter)
			.typeValue(typeValue)
			.value(value)
			.request(request)
			.build();
	}


}
