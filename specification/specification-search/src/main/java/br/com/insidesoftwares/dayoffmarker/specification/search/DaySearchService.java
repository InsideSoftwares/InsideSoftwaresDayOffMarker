package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Validated
public interface DaySearchService {

    InsideSoftwaresResponseDTO<List<DayDTO>> getAllDays(final LocalDate startDate, final LocalDate endDate, final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<DayDTO> getDayByID(final Long id);

    InsideSoftwaresResponseDTO<DayDTO> getDayByDate(final LocalDate date, final Long tagID, final String tagCode);

    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final Long tagID);

    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final String tag);

    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfCurrentMonth();

    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfMonth(final Month month, final Integer year);

    LocalDate getMaxDate();

    LocalDate getMinDate();

    boolean ownsDays();

    boolean isDayByDateAndIsWeekend(final LocalDate date, final boolean isWeekend);

    boolean isDaysWithoutHolidaysByByDayAndMonthAndFixedHolidayIDOrNotHoliday(final Integer day, final Integer month, final Long fixedHolidayID);

    Day findDayByID(final Long dayID);

    Day findDayByDate(final LocalDate date);
}
