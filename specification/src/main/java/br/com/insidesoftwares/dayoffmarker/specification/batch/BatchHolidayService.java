package br.com.insidesoftwares.dayoffmarker.specification.batch;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;

import java.time.LocalDate;

public interface BatchHolidayService {

	void createHoliday(final HolidayCreateRequestDTO holidayCreateRequestDTO);

	int getMinDateYear();

	int getMaxDateYear();

	FixedHoliday findFixedHolidayByID(final Long fixedHolidayID) throws FixedHolidayNotExistException;

	Day findDayByDate(final LocalDate daySearch);
}
