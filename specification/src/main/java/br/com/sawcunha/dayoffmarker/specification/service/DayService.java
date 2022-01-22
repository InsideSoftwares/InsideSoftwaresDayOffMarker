package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.Tag;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
@Validated
public interface DayService {

	void linkTag(final Long dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO) throws DayOffMarkerGenericException;
	void linkTagDay(final LocalDate date, final Tag tag, final Country country) throws DayOffMarkerGenericException;

	Day findDayByID(final Long dayID) throws DayOffMarkerGenericException;
	Day findDayIDByDateAndCountry(final LocalDate date, final Country country) throws DayOffMarkerGenericException;

    void setDayHoliday(final Long dayID,final boolean isHoliday) throws DayOffMarkerGenericException;

	LocalDate getMaxDate() throws DayOffMarkerGenericException;
	LocalDate getMinDate() throws DayOffMarkerGenericException;

	boolean ownsDays();
}
