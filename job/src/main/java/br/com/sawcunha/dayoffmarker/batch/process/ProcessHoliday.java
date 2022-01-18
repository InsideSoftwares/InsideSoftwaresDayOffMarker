package br.com.sawcunha.dayoffmarker.batch.process;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.commons.utils.request.RequestParametersUtils;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchUpdateHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProcessHoliday implements ItemProcessor<RequestDTO, List<HolidayRequestDTO>> {

	private final LogService<ProcessHoliday> logService;
	private final BatchUpdateHolidayService batchUpdateHolidayService;

	@PostConstruct
	public void init(){
		logService.init(ProcessHoliday.class);
		logService.logInfor("Init ProcessHoliday");
	}

	@Override
	public List<HolidayRequestDTO> process(final RequestDTO requestDTO) {

		List<HolidayRequestDTO> holidays = new ArrayList<>();

		Long fixedHolidayID = RequestParametersUtils.getFixedHolidayID(requestDTO.getRequestParameter());
		try {

			int yearMin = batchUpdateHolidayService.getMinDateYear();
			int yearMax = batchUpdateHolidayService.getMaxDateYear();

			int yearIndex = yearMin;

			FixedHoliday fixedHoliday = batchUpdateHolidayService.findFixedHolidayByID(fixedHolidayID);

			while (yearIndex <= yearMax) {

				LocalDate daySearch = LocalDate.of(yearIndex, fixedHoliday.getMonth(), fixedHoliday.getDay());
				Day day = batchUpdateHolidayService.findDayIDByDateAndCountry(daySearch, fixedHoliday.getCountry());
				if (!day.isHoliday()) {
					eTypeHoliday typeHoliday = getTypeHoliday(fixedHoliday.getFromTime(), fixedHoliday.isOptional());

					HolidayRequestDTO holidayRequestDTO = HolidayRequestDTO.builder()
							.dayId(day.getId())
							.name(fixedHoliday.getName())
							.description(fixedHoliday.getDescription())
							.fromTime(fixedHoliday.getFromTime())
							.holidayType(typeHoliday)
							.optional(fixedHoliday.isOptional())
							.build();

					holidays.add(holidayRequestDTO);
				} else {

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

	private eTypeHoliday getTypeHoliday(LocalTime from, boolean optional){
		if(Objects.isNull(from) && optional) {
			return eTypeHoliday.OPTIONAL;
		}
		return Objects.nonNull(from) && optional ? eTypeHoliday.HALF_PERIOD : eTypeHoliday.MANDATORY;
	}

}
