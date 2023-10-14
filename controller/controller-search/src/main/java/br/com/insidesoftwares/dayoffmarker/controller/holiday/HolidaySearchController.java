package br.com.insidesoftwares.dayoffmarker.controller.holiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.search.HolidaySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class HolidaySearchController {

    private final HolidaySearchService holidayService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Holiday.Read')")
    @InsideRequestGet(uri = "/v1/holiday", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_HOLIDAY")
    public InsideSoftwaresResponseDTO<List<HolidayResponseDTO>> findAll(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            InsidePaginationFilterDTO paginationFilter
    ) {
        return holidayService.findAll(startDate, endDate, paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Holiday.Read')")
    @InsideRequestGet(uri = "/v1/holiday/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_HOLIDAY")
    public InsideSoftwaresResponseDTO<HolidayResponseDTO> findById(@PathVariable Long id) {
        return holidayService.findById(id);
    }

}
