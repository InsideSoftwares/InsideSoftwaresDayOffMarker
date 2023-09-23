package br.com.insidesoftwares.dayoffmarker.service.working.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.working.WorkingDayException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.DayMapper;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.day.WorkingDayPreviousService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WorkingDayPreviousServiceBean extends WorkingDayBean implements WorkingDayPreviousService {
	private static final int LIMIT = 5;

	private final DayMapper dayMapper;

    @Override
	public DayDTO findWorkingDayPrevious( final LocalDate date, final int numberOfDays ) {
        int timesExecuted = 0;
		boolean isSearchForFutureDates = checkIfSearchFutureDates(numberOfDays);
		int range = rangeBetweenStartEnd(numberOfDays);

        LocalDate dateStartSearch = updateDateByRange(date, !isSearchForFutureDates, range);
        LocalDate dateReceived = updateDateByRange(date, isSearchForFutureDates, numberOfDays);
		LocalDate dateFinalSearch = updateDateByRange(date, isSearchForFutureDates, range);

		return findWorkingDay(dateStartSearch, dateFinalSearch, dateReceived, isSearchForFutureDates, range, timesExecuted);
	}

    private DayDTO findWorkingDay(
            LocalDate dateStartSearch,
            LocalDate dateFinalSearch,
            final LocalDate dateReceived,
            final boolean isSearchForFutureDates,
            final int range,
            final int timesExecuted
    ) {
        if(timesExecuted >= LIMIT) {
            throw new WorkingDayException();
        }

        List<Day> dates = findAllWorkingDays(dateStartSearch, dateFinalSearch, isSearchForFutureDates);

        if(!dates.isEmpty()) {
            Day result = searchForWorkDayInListOfDays(dateReceived, dates, range, false);
            if (Objects.nonNull(result)) {
                return dayMapper.toDayDTO(result);
            }
        }

        dateStartSearch = updateDateByRange(dateFinalSearch, !isSearchForFutureDates, range);
        dateFinalSearch = updateDateByRange(dateFinalSearch, isSearchForFutureDates, range);

        return findWorkingDay(dateStartSearch, dateFinalSearch, dateReceived, isSearchForFutureDates, range, timesExecuted+1);
    }

}
