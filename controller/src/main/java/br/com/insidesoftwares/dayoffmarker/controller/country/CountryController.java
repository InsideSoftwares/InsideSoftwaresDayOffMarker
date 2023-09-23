package br.com.insidesoftwares.dayoffmarker.controller.country;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.specification.service.CountryService;
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

import java.util.List;

@Tag(name = "Country", description = "Get and register the countries")
@InsideSoftwaresController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

	@Operation(
		summary = "Get All Countries",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Country.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Country.Read')")
	@InsideRequestGet(uri = "/v1/country", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_COUNTRY")
    public InsideSoftwaresResponseDTO<List<CountryResponseDTO>> findAll(
		InsidePaginationFilterDTO paginationFilter
    ) {
        return countryService.findAll(paginationFilter);
    }

	@Operation(
		summary = "Get Country by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Country.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Country.Read')")
	@InsideRequestGet(uri = "/v1/country/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_COUNTRY")
    public InsideSoftwaresResponseDTO<CountryResponseDTO> findById(@PathVariable Long id) throws CountryNotExistException {
        return countryService.findById(id);
    }

	@Operation(
		summary = "Create Country",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Country.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Country.Write')")
	@InsideRequestPost(uri = "/v1/country", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_COUNTRY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"}
	)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_COUNTRY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> save(
		@JdempotentRequestPayload @RequestBody CountryRequestDTO countryRequestDTO
    ) {
        countryService.save(countryRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Update Country by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Country.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Country.Write')")
	@InsideRequestPut(uri = "/v1/country/{id}", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_COUNTRY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_COUNTRY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
		@JdempotentRequestPayload @PathVariable Long id,
        @JdempotentRequestPayload @RequestBody CountryRequestDTO countryRequestDTO
    ) {
        countryService.update(id, countryRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
