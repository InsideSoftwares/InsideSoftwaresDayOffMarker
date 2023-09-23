package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
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
    void defineDayIsHoliday(final Long dayID);
	LocalDate getMaxDate();
	LocalDate getMinDate();
	boolean ownsDays();
	boolean isDayByDateAndIsWeekend(final LocalDate date, final boolean isWeekend);
	boolean isDaysWithoutHolidaysByByDayAndMonthAndFixedHolidayIDOrNotHoliday( final Integer day, final Integer month, final Long fixedHolidayID );
	InsideSoftwaresResponseDTO<List<DayDTO>> getAllDays( final LocalDate startDate, final LocalDate endDate, final InsidePaginationFilterDTO paginationFilter );
	InsideSoftwaresResponseDTO<DayDTO> getDayByID(final Long id);
	InsideSoftwaresResponseDTO<DayDTO> getDayByDate(final LocalDate date, final Long tagID, final String tagCode);
	InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final Long tagID);
	InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final String tag);
	InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfCurrentMonth();
	InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfMonth(final Month month, final Integer year);
}
