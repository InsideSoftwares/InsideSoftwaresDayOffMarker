package br.com.insidesoftwares.dayoffmarker.controller.city;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestDelete;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.city.CityService;
import br.com.insidesoftwares.jdempotent.core.annotation.JdempotentRequestPayload;
import br.com.insidesoftwares.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
    @InsideRequestPost(uri = "/v1/city", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponseDTO<UUID> save(
            @JdempotentRequestPayload @RequestBody CityRequestDTO cityRequestDTO
    ) {
        return cityService.save(cityRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
    @InsideRequestPut(uri = "/v1/city/{id}", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody CityRequestDTO cityRequestDTO
    ) {
        cityService.update(id, cityRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
    @InsideRequestPost(uri = "/v1/city/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> addCityHoliday(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody CityHolidayRequestDTO cityHolidayRequestDTO
    ) {
        cityService.addCityHoliday(id, cityHolidayRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
    @InsideRequestDelete(uri = "/v1/city/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> deleteCityHoliday(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO
    ) {
        cityService.deleteCityHoliday(id, cityHolidayDeleteRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }
}
