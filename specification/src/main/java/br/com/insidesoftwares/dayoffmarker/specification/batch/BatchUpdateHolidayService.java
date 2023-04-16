package br.com.insidesoftwares.dayoffmarker.specification.batch;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.entity.FixedHoliday;

import java.time.LocalDate;

public interface BatchUpdateHolidayService {

	void updateHoliday(final HolidayRequestDTO holidayRequestDTO);

	int getMinDateYear();

	int getMaxDateYear();

	FixedHoliday findFixedHolidayByID(final Long fixedHolidayID) throws FixedHolidayNotExistException;

	Day findDayByDate(final LocalDate daySearch);
}
