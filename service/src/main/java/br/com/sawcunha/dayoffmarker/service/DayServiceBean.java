package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DaysNotConfiguredException;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayServiceBean implements DayService {

    private final DayRepository dayRepository;
	private static final LocalDate CUT_OFF_DATE = LocalDate.now();

    @Transactional(readOnly = true)
    @Override
    public Day findDayByID(final Long dayID) throws DayOffMarkerGenericException {
        Optional<Day> optionalDay = dayRepository.findById(dayID);
        return optionalDay.orElseThrow(DayNotExistException::new);
    }

	@Override
	public Day findDayIDByDateAndCountry(final LocalDate date, final Country country) throws DayOffMarkerGenericException {
		Optional<Day> optionalDay = dayRepository.findByDateAndCountry(date,country);
		return optionalDay.orElseThrow(DayNotExistException::new);
	}

	@Transactional(rollbackFor = {
            DayNotExistException.class,
    })
    @Override
    public void setDayHoliday(final Long dayID, final boolean isHoliday) throws DayOffMarkerGenericException {
        Day day = findDayByID(dayID);
        day.setHoliday(isHoliday);
        dayRepository.save(day);
    }

	@Transactional(readOnly = true)
	@Override
	public LocalDate getMaxDate() throws DayOffMarkerGenericException {
		return dayRepository.findMaxDateByDate(CUT_OFF_DATE).orElseThrow(DaysNotConfiguredException::new);
	}

	@Transactional(readOnly = true)
	@Override
	public LocalDate getMinDate() throws DayOffMarkerGenericException {
		return dayRepository.findMinDateByDate(CUT_OFF_DATE).orElseThrow(DaysNotConfiguredException::new);
	}

	@Override
	public boolean ownsDays() {
		return dayRepository.ownsDays(CUT_OFF_DATE);
	}
}
