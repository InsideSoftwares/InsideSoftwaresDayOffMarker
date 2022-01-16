package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;

import java.time.LocalDate;

public interface DayService {

	Day findDayByID(final Long dayID) throws Exception;
	Day findDayIDByDateAndCountry(final LocalDate date, final Country country) throws Exception;

    void setDayHoliday(final Long dayID,final boolean isHoliday) throws Exception;

	LocalDate getMaxDate() throws Exception;
	LocalDate getMinDate() throws Exception;

	boolean ownsDays();
}
