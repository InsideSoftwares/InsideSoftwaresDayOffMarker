package br.com.insidesoftwares.dayoffmarker.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.working.WorkingDayException;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.mapper.DayMapper;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class WorkingDayServiceBean implements WorkingDayService {
	private static final int LIMIT = 5;

	private final DayRepository dayRepository;
	private final DayMapper dayMapper;

	@Override
	public InsideSoftwaresResponse<DayDTO> findWorkingDay(
		final LocalDate date,
		final int numberOfDays
	) {
		DayDTO dayDTO = findWorkingDay(date, numberOfDays, false);

		return InsideSoftwaresResponseUtils.wrapResponse(dayDTO);
	}

	@Override
	public InsideSoftwaresResponse<DayDTO> findPreviousWorkingDay(
		final LocalDate date,
		final int numberOfDays
	) {
		DayDTO dayDTO = findWorkingDay(date, numberOfDays, true);

		return InsideSoftwaresResponseUtils.wrapResponse(dayDTO);
	}

	@Override
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDay() {
		LocalDate currentDay = LocalDate.now();

		boolean isWorkingDay = dayRepository.isWorkingDayByDateAndIsHolidayAndIsWeekend(currentDay, false, false);

		return InsideSoftwaresResponseUtils.wrapResponse(
			WorkingCurrentDayResponseDTO.builder()
				.isWorkingDay(isWorkingDay)
				.build()
		);
	}

	private DayDTO findWorkingDay(
		final LocalDate date,
		final int numberOfDays,
		final boolean previous
	) {

		boolean future = checkIfSearchFuture(numberOfDays);
		int range = rangeBetweenStartEnd(numberOfDays);

		LocalDate dateStartSearch = date;
		LocalDate dateReceived = updateDateByRange(date, future, numberOfDays);
		LocalDate dateFinalSearch = updateDateByRange(dateStartSearch, future, range);
		dateStartSearch = adjustDateWithPrevious(dateStartSearch, range, future, previous);
		int timesExecuted = 0;
		do {
			List<Day> dates = getWorkingDates(dateStartSearch, dateFinalSearch, future);

			if(!dates.isEmpty()) {
				Day result = getWorkingDay(dateReceived, dates, range, previous);
				if (Objects.nonNull(result)) {
					return dayMapper.toDayDTO(result);
				}
			}

			timesExecuted++;

			dateStartSearch = dateFinalSearch;
			dateFinalSearch = updateDateByRange(dateStartSearch, future, range);
			dateStartSearch = adjustDateWithPrevious(dateStartSearch, range, future, previous);

		}while(timesExecuted < LIMIT);

		throw new WorkingDayException();
	}

	private LocalDate adjustDateWithPrevious(final LocalDate data, final int range, final boolean future, final boolean previous) {
		return previous ? updateDateByRange(data, !future, range) : data;
	}
	private boolean checkIfSearchFuture(final int numberOfDays) {
		return numberOfDays >= 0;
	}

	private int rangeBetweenStartEnd(final int numberOfDays) {
		int MINUMO = 5;
		int MUTIPLICATOR = 2;
		int numberAbsOfDays = Math.abs(numberOfDays);
		return Math.max(MINUMO, MUTIPLICATOR * numberAbsOfDays);
	}

	private List<Day> getWorkingDates(
		final LocalDate dateStartSearch,
		final LocalDate dateFinalSearch,
		final boolean future
	) {
		boolean isHoliday = false;
		boolean isWeekend = false;

		return future ?
			dayRepository.findAllByDateSearchAndHolidayAndWeekend(dateStartSearch, dateFinalSearch, isHoliday, isWeekend) :
			dayRepository.findAllByDateSearchAndHolidayAndWeekend(dateFinalSearch, dateStartSearch, isHoliday, isWeekend);
	}

	private Day getWorkingDay(final LocalDate dateReceived, final List<Day> dates, final int range, final boolean previous) {
		Optional<Day> dateOptional = getDayByDate(dates, dateReceived);

		return dateOptional.orElseGet(() -> {
			LocalDate novadateReceived = dateReceived;
			for(int index = 1; index < range; index++) {
				novadateReceived = updateDateByRange(novadateReceived, !previous, 1);
				Optional<Day> dayOptional = getDayByDate(dates, novadateReceived);
				if (dayOptional.isPresent()){
					return  dayOptional.get();
				}
			}
			return null;
		});
	}

	private Optional<Day> getDayByDate(final List<Day> dates, final LocalDate dateReceived){
		return dates.stream().filter(data -> data.getDate().isEqual(dateReceived)).findFirst();
	}

	private LocalDate updateDateByRange(final LocalDate data, final boolean future, final int range){
		int rangeAbs = Math.abs(range);
		return future ? data.plusDays(rangeAbs) : data.minusDays(rangeAbs);
	}


}
