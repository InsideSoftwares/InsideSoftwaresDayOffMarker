package br.com.insidesoftwares.dayoffmarker.controller.state;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.search.state.StateSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class StateSearchController {

    private final StateSearchService stateService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.State.Read')")
    @InsideRequestGet(uri = "/v1/state", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_STATE")
    public InsideSoftwaresResponseDTO<List<StateResponseDTO>> findAll(
            @RequestParam(value = "country", required = false) String nameCountry,
            InsidePaginationFilterDTO paginationFilter
    ) {
        return stateService.findAll(nameCountry, paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.State.Read')")
    @InsideRequestGet(uri = "/v1/state/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_STATE")
    public InsideSoftwaresResponseDTO<StateResponseDTO> findById(@PathVariable UUID id) {
        return stateService.findById(id);
    }
}
