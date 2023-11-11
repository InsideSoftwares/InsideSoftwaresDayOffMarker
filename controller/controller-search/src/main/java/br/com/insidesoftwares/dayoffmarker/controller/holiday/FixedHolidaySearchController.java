package br.com.insidesoftwares.dayoffmarker.controller.holiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.specification.search.holiday.FixedHolidaySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class FixedHolidaySearchController {

    private final FixedHolidaySearchService fixedHolidayService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.FixedHoliday.Read')")
    @InsideRequestGet(uri = "/v1/fixed-holiday", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_FIXEDHOLIDAY")
    public InsideSoftwaresResponseDTO<List<FixedHolidayResponseDTO>> findAll(
            InsidePaginationFilterDTO paginationFilter
    ) {
        return fixedHolidayService.findAll(paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.FixedHoliday.Read')")
    @InsideRequestGet(uri = "/v1/fixed-holiday/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_FIXEDHOLIDAY")
    public InsideSoftwaresResponseDTO<FixedHolidayResponseDTO> findById(@PathVariable UUID id) throws FixedHolidayNotExistException {
        return fixedHolidayService.findById(id);
    }

}
