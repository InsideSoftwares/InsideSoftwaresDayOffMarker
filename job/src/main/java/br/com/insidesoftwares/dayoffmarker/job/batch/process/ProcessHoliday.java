package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchUpdateHolidayService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProcessHoliday implements ItemProcessor<Request, List<HolidayRequestDTO>> {

	private final LogService<ProcessHoliday> logService;
	private final BatchUpdateHolidayService batchUpdateHolidayService;
	private final BatchCreationDayService batchCreationDayService;

	@PostConstruct
	public void init(){
		logService.init(ProcessHoliday.class);
		logService.logInfor("Init ProcessHoliday");
	}

	@Override
	public List<HolidayRequestDTO> process(final Request request) {

		List<HolidayRequestDTO> holidays = new ArrayList<>();

		Long fixedHolidayID = RequestParametersUtils.getFixedHolidayID(request.getRequestParameter());
		try {

			int yearMin = batchUpdateHolidayService.getMinDateYear();
			int yearMax = batchUpdateHolidayService.getMaxDateYear();

			int yearIndex = yearMin;

			FixedHoliday fixedHoliday = batchUpdateHolidayService.findFixedHolidayByID(fixedHolidayID);

			while (yearIndex <= yearMax) {

				if(DateUtils.isDateValid(fixedHoliday.getDay(), fixedHoliday.getMonth(), yearIndex)) {
					LocalDate daySearch = LocalDate.of(yearIndex, fixedHoliday.getMonth(), fixedHoliday.getDay());
					if(batchCreationDayService.existDayInDayBatch(daySearch)) {
						Day day = batchUpdateHolidayService.findDayByDate(daySearch);
						boolean isUpdate = Objects.nonNull(day.getHolidays()) && day.getHolidays().stream().anyMatch(Holiday::isAutomaticUpdate);
						if (!day.isHoliday() || isUpdate) {
							TypeHoliday typeHoliday = getTypeHoliday(fixedHoliday.getFromTime(), fixedHoliday.isOptional());

							HolidayRequestDTO holidayRequestDTO = HolidayRequestDTO.builder()
								.dayId(day.getId())
								.name(fixedHoliday.getName())
								.description(fixedHoliday.getDescription())
								.fromTime(fixedHoliday.getFromTime())
								.holidayType(typeHoliday)
								.optional(fixedHoliday.isOptional())
								.build();

							holidays.add(holidayRequestDTO);
						}
					}
				}
				yearIndex++;
			}
		} catch (Exception e) {
			assert fixedHolidayID != null;
			logService.logErrorByArgs("Could not create the holiday: {}", fixedHolidayID.toString());
			logService.logError("Could not create the holiday", e);
		}

		return holidays;
	}

	private TypeHoliday getTypeHoliday(LocalTime from, boolean optional){
		if(Objects.isNull(from) && optional) {
			return TypeHoliday.OPTIONAL;
		}
		return Objects.nonNull(from) && optional ? TypeHoliday.HALF_PERIOD : TypeHoliday.MANDATORY;
	}

}
