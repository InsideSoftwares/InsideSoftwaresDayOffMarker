package br.com.insidesoftwares.dayoffmarker.controller.state;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestDelete;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.state.StateService;
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
public class StateController {

    private final StateService stateService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
    @InsideRequestPost(uri = "/v1/state", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponseDTO<UUID> save(
            @JdempotentRequestPayload @RequestBody StateRequestDTO stateRequestDTO
    ) {
        return stateService.save(stateRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
    @InsideRequestPut(uri = "/v1/state/{id}", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody StateRequestDTO stateRequestDTO
    ) {
        stateService.update(id, stateRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
    @InsideRequestPost(uri = "/v1/state/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> addCityHoliday(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody StateHolidayRequestDTO stateHolidayRequestDTO
    ) {
        stateService.addStateHoliday(id, stateHolidayRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.State.Write')")
    @InsideRequestDelete(uri = "/v1/state/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_STATE", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> deleteCityHoliday(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO
    ) {
        stateService.deleteStateHoliday(id, stateHolidayDeleteRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
