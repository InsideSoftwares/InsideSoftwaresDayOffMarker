package br.com.insidesoftwares.dayoffmarker.controller.working;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingCityService;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingDayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingStateService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "Working", description = "Search days of work.")
@InsideSoftwaresController
@RequiredArgsConstructor
public class WorkingController {

	private final WorkingDayService workingDayService;
	private final WorkingStateService workingStateService;
	private final WorkingCityService workingCityService;

	@Operation(
		summary = "Valid if the day is a working day or returns the next day",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingDay.Read')")
	@InsideRequestGet(uri = "/v1/working/day/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<DayDTO> findWorkingDay(
		@PathVariable final LocalDate date,
		@RequestParam(value = "numberOfDays", required = false, defaultValue = "0") final int numberOfDays
	) {
		return workingDayService.findWorkingDay(date, numberOfDays);
	}

	@Operation(
		summary = "Valid if the day is a working day or returns the previous day",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingDay.Read')")
	@InsideRequestGet(uri = "/v1/working/day/previous/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<DayDTO> findPreviousWorkingDay(
		@PathVariable final LocalDate date,
		@RequestParam(value = "numberOfDays", required = false, defaultValue = "0") final int numberOfDays
	) {
		return workingDayService.findPreviousWorkingDay(date, numberOfDays);
	}

	@Operation(
		summary = "Returns if the current day is a working day",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingDay.Read')")
	@InsideRequestGet(uri = "/v1/working/current/day", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDay() {
		return workingDayService.findWorkingCurrentDay();
	}

	@Operation(
		summary = "Returns if the informed day is a working day in the state",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
	@InsideRequestGet(uri = "/v1/working/day/{date}/state/{stateID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingStateByDay(
		@PathVariable final LocalDate date,
		@PathVariable final Long stateID
	) {
		return workingStateService.findWorkingStateByDay(stateID, date);
	}

	@Operation(
		summary = "Returns whether the current day is a working day in the state",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
	@InsideRequestGet(uri = "/v1/working/current/day/state/{stateID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(
		@PathVariable final Long stateID
	) {
		return workingStateService.findWorkingCurrentDayState(stateID);
	}

	@Operation(
		summary = "Returns if the informed day is a working day in the city",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
	@InsideRequestGet(uri = "/v1/working/day/{date}/city/{cityID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCityByDay(
		@PathVariable final LocalDate date,
		@PathVariable final Long cityID
	) {
		return workingCityService.findWorkingCityByDay(cityID, date);
	}

	@Operation(
		summary = "Returns whether the current day is a working day in the city",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.WorkingDay.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
	@InsideRequestGet(uri = "/v1/working/current/day/city/{cityID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(
		@PathVariable final Long cityID
	) {
		return workingCityService.findWorkingCurrentDayCity(cityID);
	}

}
