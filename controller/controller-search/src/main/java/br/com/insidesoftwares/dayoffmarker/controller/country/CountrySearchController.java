package br.com.insidesoftwares.dayoffmarker.controller.country;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.specification.search.country.CountrySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class CountrySearchController {

    private final CountrySearchService countryService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Country.Read')")
    @InsideRequestGet(uri = "/v1/country", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_COUNTRY")
    public InsideSoftwaresResponseDTO<List<CountryResponseDTO>> findAll(
            InsidePaginationFilterDTO paginationFilter
    ) {
        return countryService.findAll(paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Country.Read')")
    @InsideRequestGet(uri = "/v1/country/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_COUNTRY")
    public InsideSoftwaresResponseDTO<CountryResponseDTO> findById(@PathVariable UUID id) throws CountryNotExistException {
        return countryService.findById(id);
    }

}
