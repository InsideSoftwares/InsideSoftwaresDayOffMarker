package br.com.insidesoftwares.dayoffmarker.controller.city;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestDelete;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.specification.service.CityService;
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

@Tag(name = "City", description = "Get and register the cities")
@InsideSoftwaresController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

	@Operation(
		summary = "Get All Cities",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.City.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.City.Read')")
	@InsideRequestGet(uri = "/v1/city", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_CITY")
	public InsideSoftwaresResponseDTO<List<CityResponseDTO>> findAll(
		@RequestParam(value = "stateID", required = false) Long stateID,
		InsidePaginationFilterDTO<eOrderCity> paginationFilter
    ) {
        return cityService.findAll(stateID, paginationFilter);
    }

	@Operation(
		summary = "Get City by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.City.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.City.Read')")
	@InsideRequestGet(uri = "/v1/city/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_CITY")
    public InsideSoftwaresResponseDTO<CityResponseDTO> findById(@PathVariable Long id) {
        return cityService.findById(id);
    }

	@Operation(
		summary = "Create City",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.City.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestPost(uri = "/v1/city", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> save(
            @JdempotentRequestPayload @RequestBody CityRequestDTO cityRequestDTO
    ) {
        cityService.save(cityRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Update City by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.City.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestPut(uri = "/v1/city/{id}", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody CityRequestDTO cityRequestDTO
    ) {
        cityService.update(id, cityRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Add holidays to City",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.City.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestPost(uri = "/v1/city/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
	public InsideSoftwaresResponseDTO<Void> addCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody CityHolidayRequestDTO cityHolidayRequestDTO
	) {
		cityService.addCityHoliday(id, cityHolidayRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
	}

	@Operation(
		summary = "Remove holidays to City",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.City.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestDelete(uri = "/v1/city/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
	public InsideSoftwaresResponseDTO<Void> deleteCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO
	) {
		cityService.deleteCityHoliday(id, cityHolidayDeleteRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
	}
}
