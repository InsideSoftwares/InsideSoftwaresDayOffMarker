package br.com.insidesoftwares.dayoffmarker.specification.search.day;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

@Validated
public interface DaySearchService {

    InsideSoftwaresResponseDTO<List<DayDTO>> getAllDays(final LocalDate startDate, final LocalDate endDate, final InsidePaginationFilterDTO paginationFilter);
    InsideSoftwaresResponseDTO<DayDTO> getDayByID(final UUID id);
    InsideSoftwaresResponseDTO<DayDTO> getDayByDate(final LocalDate date, final UUID tagID, final String tagCode);
    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final UUID tagID);
    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final String tag);
    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfCurrentMonth();
    InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfMonth(final Month month, final Integer year);
    boolean isDayByDateAndIsWeekend(final LocalDate date, final boolean isWeekend);
    Day findDayByID(final UUID dayID);
}
