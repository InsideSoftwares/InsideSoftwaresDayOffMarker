package br.com.sawcunha.dayoffmarker.scheduled.holiday;

import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import br.com.sawcunha.dayoffmarker.specification.service.FixedHolidayService;
import br.com.sawcunha.dayoffmarker.specification.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class HolidayJobServiceBean {

	private final HolidayService holidayService;
	private final DayService dayService;
	private final FixedHolidayService fixedHolidayService;
	private final LogService<HolidayJobServiceBean> logService;

	@PostConstruct
	public void init(){
		logService.init(HolidayJobServiceBean.class);
		logService.logInfor("Init HolidayJobServiceBean");
	}

	public void createHolidayFromFixedHoliday(){
		try {
			logService.logInfor("Starting the creation of holidays.");
			if(dayService.ownsDays()) {
				logService.logInfor("Creating the Holidays.");
				List<FixedHoliday> fixedHolidays = fixedHolidayService.findAllByCountry();

				int yearMin = dayService.getMinDate().getYear();
				int yearMax = dayService.getMaxDate().getYear();

				fixedHolidays.forEach(fixedHoliday -> {
					int yearIndex = yearMin;
					try {

						while (yearIndex <= yearMax) {

							LocalDate daySearch = LocalDate.of(yearIndex, fixedHoliday.getMonth(), fixedHoliday.getDay());
							Day day = dayService.findDayIDByDateAndCountry(daySearch, fixedHoliday.getCountry());
							if (!day.isHoliday()) {
								eTypeHoliday typeHoliday = getTypeHoliday(fixedHoliday.getFromTime(), fixedHoliday.isOptional());

								HolidayRequestDTO holidayRequestDTO = HolidayRequestDTO.builder()
										.dayId(day.getId())
										.name(fixedHoliday.getName())
										.description(fixedHoliday.getDescription())
										.fromTime(fixedHoliday.getFromTime())
										.holidayType(typeHoliday)
										.build();

								holidayService.saveHoliday(holidayRequestDTO);
							}

							yearIndex++;
						}
					} catch (Exception e) {
						logService.logErrorByArgs("Could not create the holiday: {} - {}", fixedHoliday.getId().toString(), fixedHoliday.getName());
						logService.logError("Could not create the holiday", e);
					}
				});
			}

		}catch (Exception e){
			logService.logError("It was not possible to create the Holidays.", e);
		} finally {
			logService.logInfor("Finishing the creation of Holidays.");
		}
	}


	private eTypeHoliday getTypeHoliday(LocalTime from, boolean optional){
		if(Objects.isNull(from) && optional) {
			return eTypeHoliday.OPTIONAL;
		}
		return Objects.nonNull(from) && optional ? eTypeHoliday.HALF_PERIOD : eTypeHoliday.MANDATORY;
	}
}
