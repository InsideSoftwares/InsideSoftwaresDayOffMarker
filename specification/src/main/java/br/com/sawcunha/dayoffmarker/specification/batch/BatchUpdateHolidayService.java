package br.com.sawcunha.dayoffmarker.specification.batch;

import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;

import java.time.LocalDate;

public interface BatchUpdateHolidayService {

	void updateHoliday(final HolidayRequestDTO holidayRequestDTO);

	int getMinDateYear() throws DayOffMarkerGenericException;

	int getMaxDateYear() throws DayOffMarkerGenericException;

	FixedHoliday findFixedHolidayByID(final Long fixedHolidayID) throws FixedHolidayNotExistException;

	Day findDayIDByDateAndCountry(final LocalDate daySearch,final  Country country) throws DayOffMarkerGenericException;
}
