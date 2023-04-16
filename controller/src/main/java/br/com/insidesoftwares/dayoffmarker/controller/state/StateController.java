package br.com.insidesoftwares.dayoffmarker.controller.state;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestDelete;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderState;
import br.com.insidesoftwares.dayoffmarker.specification.service.StateService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.State.Read')")
	@InsideRequestGet(uri = "/v1/state", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_STATE")
    public InsideSoftwaresResponse<List<StateResponseDTO>> findAll(
            @RequestParam(value = "country", required = false) String nameCountry,
			PaginationFilter<eOrderState> paginationFilter
    ) {
        return stateService.findAll(nameCountry, paginationFilter);
    }

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.State.Read')")
	@InsideRequestGet(uri = "/v1/state/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_STATE")
    public InsideSoftwaresResponse<StateResponseDTO> findById(@PathVariable Long id) {
        return stateService.findById(id);
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestPost(uri = "/v1/state", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponse<Void> save(
		@JdempotentRequestPayload @RequestBody StateRequestDTO stateRequestDTO
    ) {
        stateService.save(stateRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestPut(uri = "/v1/state/{id}", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponse<Void> update(
            @PathVariable Long id,
            @RequestBody StateRequestDTO stateRequestDTO
    ) {
        stateService.update(id, stateRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestPost(uri = "/v1/state/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
	public InsideSoftwaresResponse<Void> addCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody StateHolidayRequestDTO stateHolidayRequestDTO
	) {
		stateService.addStateHoliday(id, stateHolidayRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestDelete(uri = "/v1/state/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
	public InsideSoftwaresResponse<Void> deleteCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO
	) {
		stateService.deleteStateHoliday(id, stateHolidayDeleteRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

}
