package br.com.insidesoftwares.dayoffmarker.controller.city;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.search.CitySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class CitySearchController {

    private final CitySearchService citySearchService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.City.Read')")
    @InsideRequestGet(uri = "/v1/city", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_CITY")
    public InsideSoftwaresResponseDTO<List<CityResponseDTO>> findAll(
            @RequestParam(value = "stateID", required = false) Long stateID,
            InsidePaginationFilterDTO paginationFilter
    ) {
        return citySearchService.findAll(stateID, paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.City.Read')")
    @InsideRequestGet(uri = "/v1/city/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_CITY")
    public InsideSoftwaresResponseDTO<CityResponseDTO> findById(@PathVariable Long id) {
        return citySearchService.findById(id);
    }
}
