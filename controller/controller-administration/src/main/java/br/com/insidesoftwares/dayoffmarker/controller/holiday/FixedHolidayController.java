package br.com.insidesoftwares.dayoffmarker.controller.holiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.holiday.FixedHolidayService;
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
public class FixedHolidayController {

    private final FixedHolidayService fixedHolidayService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
    @InsideRequestPost(uri = "/v1/fixed-holiday", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<UUID> save(
            @JdempotentRequestPayload @RequestBody FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) {
        return fixedHolidayService.save(fixedHolidayRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
    @InsideRequestPut(uri = "/v1/fixed-holiday/{id}", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO
    ) {
        fixedHolidayService.update(id, fixedHolidayRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
