package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderDay;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Validated
public interface DayService {

	void linkTag(final Long dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO);
	void unlinkTag(final Long dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO);

	Day findDayByID(final Long dayID);
	Day findDayByDate(final LocalDate date);

    void setDayHoliday(final Long dayID,final boolean isHoliday);

	LocalDate getMaxDate();
	LocalDate getMinDate();

	boolean ownsDays();

	boolean isDayByDateAndIsHolidayAndIsWeekend(final LocalDate date, final boolean isHoliday, final boolean isWeekend);

	boolean isDayByDateAndIsWeekend(final LocalDate date, final boolean isWeekend);

	boolean isDaysWithoutHolidaysByByDayAndMonthAndFixedHolidayIDOrNotHoliday(
		final Integer day,
		final Integer month,
		final Long fixedHolidayID
	);

	InsideSoftwaresResponse<List<DayDTO>> getAllDays(
		final LocalDate startDate,
		final LocalDate endDate,
		final PaginationFilter<eOrderDay> paginationFilter
	);
	InsideSoftwaresResponse<DayDTO> getDayByID(final Long id);
	InsideSoftwaresResponse<DayDTO> getDayByDate(final LocalDate date, final Long tagID, final String tagCode);

	InsideSoftwaresResponse<List<DayDTO>> getDaysByTag(final Long tagID);
	InsideSoftwaresResponse<List<DayDTO>> getDaysByTag(final String tag);

	InsideSoftwaresResponse<List<DayDTO>> getDaysOfCurrentMonth();

	InsideSoftwaresResponse<List<DayDTO>> getDaysOfMonth(final Month month, final Integer year);
}
