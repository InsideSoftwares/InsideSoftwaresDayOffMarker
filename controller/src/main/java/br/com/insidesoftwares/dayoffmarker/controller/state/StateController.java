package br.com.insidesoftwares.dayoffmarker.controller.state;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestDelete;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderState;
import br.com.insidesoftwares.dayoffmarker.specification.service.StateService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "State", description = "Get and register the states")
@InsideSoftwaresController
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

	@Operation(
		summary = "Get All States",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.State.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.State.Read')")
	@InsideRequestGet(uri = "/v1/state", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_STATE")
    public InsideSoftwaresResponseDTO<List<StateResponseDTO>> findAll(
            @RequestParam(value = "country", required = false) String nameCountry,
			InsidePaginationFilterDTO<eOrderState> paginationFilter
    ) {
        return stateService.findAll(nameCountry, paginationFilter);
    }

	@Operation(
		summary = "Get State by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.State.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.State.Read')")
	@InsideRequestGet(uri = "/v1/state/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_STATE")
    public InsideSoftwaresResponseDTO<StateResponseDTO> findById(@PathVariable Long id) {
        return stateService.findById(id);
    }

	@Operation(
		summary = "Create State",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.State.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestPost(uri = "/v1/state", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> save(
		@JdempotentRequestPayload @RequestBody StateRequestDTO stateRequestDTO
    ) {
        stateService.save(stateRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Update state by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.State.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestPut(uri = "/v1/state/{id}", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody StateRequestDTO stateRequestDTO
    ) {
        stateService.update(id, stateRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Add holidays to State by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.State.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestPost(uri = "/v1/state/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
	public InsideSoftwaresResponseDTO<Void> addCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody StateHolidayRequestDTO stateHolidayRequestDTO
	) {
		stateService.addStateHoliday(id, stateHolidayRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
	}

	@Operation(
		summary = "Remove holidays to State by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.State.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
	@InsideRequestDelete(uri = "/v1/state/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
	public InsideSoftwaresResponseDTO<Void> deleteCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO
	) {
		stateService.deleteStateHoliday(id, stateHolidayDeleteRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
	}

}
