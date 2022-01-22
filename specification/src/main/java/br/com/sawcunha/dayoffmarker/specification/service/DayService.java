package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;

import java.time.LocalDate;

public interface DayService {

	Day findDayByID(final Long dayID) throws DayOffMarkerGenericException;
	Day findDayIDByDateAndCountry(final LocalDate date, final Country country) throws DayOffMarkerGenericException;

    void setDayHoliday(final Long dayID,final boolean isHoliday) throws DayOffMarkerGenericException;

	LocalDate getMaxDate() throws DayOffMarkerGenericException;
	LocalDate getMinDate() throws DayOffMarkerGenericException;

	boolean ownsDays();
}
