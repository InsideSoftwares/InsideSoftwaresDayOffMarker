package br.com.insidesoftwares.dayoffmarker.controller.day;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.specification.search.day.DaySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class DaySearchController {

    private final DaySearchService dayService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findAll(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            InsidePaginationFilterDTO paginationFilter
    ) {
        return dayService.getAllDays(startDate, endDate, paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<DayDTO> findById(@PathVariable final UUID id) {
        return dayService.getDayByID(id);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/date/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<DayDTO> findDayByDate(
            @PathVariable final LocalDate date,
            @RequestParam(value = "tagID", required = false) UUID tagID,
            @RequestParam(value = "tagCode", required = false) String tagCode
    ) {
        return dayService.getDayByDate(date, tagID, tagCode);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/tag/{tagID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysByTag(
            @PathVariable final UUID tagID
    ) {
        return dayService.getDaysByTag(tagID);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/tag/code/{codeTag}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysByTag(
            @PathVariable final String codeTag
    ) {
        return dayService.getDaysByTag(codeTag);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/current/month", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysOfCurrentMonth() {
        return dayService.getDaysOfCurrentMonth();
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/month/{month}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysOfMonth(
            @PathVariable final Month month,
            @RequestParam(value = "year", required = false) Integer year
    ) {
        return dayService.getDaysOfMonth(month, year);
    }

}
