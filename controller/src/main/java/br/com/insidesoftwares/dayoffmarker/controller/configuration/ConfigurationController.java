package br.com.insidesoftwares.dayoffmarker.controller.configuration;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationCountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration.ConfigurationLimitYearRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;

@InsideSoftwaresController
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

	@PreAuthorize("hasAnyRole('DayOff.Write')")
	@InsideRequestPost(uri = "/v1/configuration/limit/year", httpCode = HttpStatus.ACCEPTED, nameCache = "ALL")
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP", ttl = 1)
    public InsideSoftwaresResponse<Void> configurationLimitYear(
            @JdempotentRequestPayload @RequestBody final ConfigurationLimitYearRequestDTO configurationLimitYearRequestDTO
    ) {
        configurationService.configurationLimitYear(configurationLimitYearRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write')")
	@InsideRequestPost(uri = "/v1/configuration/country", httpCode = HttpStatus.ACCEPTED, nameCache = "ALL")
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP", ttl = 1)
	public InsideSoftwaresResponse<Void> configurationCountry(
		@JdempotentRequestPayload @RequestBody final ConfigurationCountryRequestDTO configurationCountryRequestDTO
	) {
		configurationService.configurationCountry(configurationCountryRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}



}
