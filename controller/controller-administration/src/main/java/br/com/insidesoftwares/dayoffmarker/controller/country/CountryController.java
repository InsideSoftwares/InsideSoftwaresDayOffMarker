package br.com.insidesoftwares.dayoffmarker.controller.country;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.country.CountryService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Country.Write')")
    @InsideRequestPost(uri = "/v1/country", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_COUNTRY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_COUNTRY", ttl = 1)
    public InsideSoftwaresResponseDTO<UUID> save(
            @JdempotentRequestPayload @RequestBody CountryRequestDTO countryRequestDTO
    ) {
        return countryService.save(countryRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Country.Write')")
    @InsideRequestPut(uri = "/v1/country/{id}", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_COUNTRY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_COUNTRY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody CountryRequestDTO countryRequestDTO
    ) {
        countryService.update(id, countryRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
