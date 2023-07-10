package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchHolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessHoliday implements ItemProcessor<Request, List<HolidayCreateRequestDTO>> {

	private final BatchHolidayService batchHolidayService;
	private final BatchCreationDayService batchCreationDayService;

	@Override
	public List<HolidayCreateRequestDTO> process(final Request request) {

		List<HolidayCreateRequestDTO> holidays = new ArrayList<>();

		Long fixedHolidayID = RequestParametersUtils.getFixedHolidayID(request.getRequestParameter());
		try {

			int yearMin = batchHolidayService.getMinDateYear();
			int yearMax = batchHolidayService.getMaxDateYear();

			int yearIndex = yearMin;

			FixedHoliday fixedHoliday = batchHolidayService.findFixedHolidayByID(fixedHolidayID);

			while (yearIndex <= yearMax) {

				if(DateUtils.isDateValid(fixedHoliday.getDay(), fixedHoliday.getMonth(), yearIndex)) {
					LocalDate daySearch = LocalDate.of(yearIndex, fixedHoliday.getMonth(), fixedHoliday.getDay());
					if(batchCreationDayService.existDayInDayBatch(daySearch)) {

						Day day = batchHolidayService.findDayByDate(daySearch);
						TypeHoliday typeHoliday = getTypeHoliday(fixedHoliday.getFromTime(), fixedHoliday.isOptional());

						boolean isHoliday = day.getHolidays().stream().anyMatch(holiday -> holiday.getFixedHolidayID().equals(fixedHolidayID));

						if(!isHoliday) {
							HolidayCreateRequestDTO holidayRequestDTO = HolidayCreateRequestDTO.builder()
								.dayId(day.getId())
								.name(fixedHoliday.getName())
								.description(fixedHoliday.getDescription())
								.fromTime(fixedHoliday.getFromTime())
								.holidayType(typeHoliday)
								.optional(fixedHoliday.isOptional())
								.fixedHolidayID(fixedHolidayID)
								.build();

							holidays.add(holidayRequestDTO);
						}
					}
				}
				yearIndex++;
			}
		} catch (Exception e) {
			assert fixedHolidayID != null;
			log.error("Could not create the holiday: {}", fixedHolidayID);
			log.error("Could not create the holiday", e);
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
