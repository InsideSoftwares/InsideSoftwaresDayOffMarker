package br.com.insidesoftwares.dayoffmarker.controller.configuration;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
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
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Configuration", description = "Performs basic system settings.")
@InsideSoftwaresController
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

	@Operation(
		summary = "Set limit of days that can be created",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write')")
	@InsideRequestPost(uri = "/v1/configuration/limit/year", httpCode = HttpStatus.ACCEPTED, nameCache = "ALL")
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> configurationLimitYear(
		@JdempotentRequestPayload @RequestBody final ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO
    ) {
        configurationService.configurationLimitYear(configurationLimitYearRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Defines the application's default country",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write')")
	@InsideRequestPost(uri = "/v1/configuration/country", httpCode = HttpStatus.ACCEPTED, nameCache = "ALL")
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP", ttl = 1)
	public InsideSoftwaresResponseDTO<Void> configurationCountry(
		@JdempotentRequestPayload @RequestBody final ConfigurationCountryRequestDTO configurationCountryRequestDTO
	) {
		configurationService.configurationCountry(configurationCountryRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
	}



}
