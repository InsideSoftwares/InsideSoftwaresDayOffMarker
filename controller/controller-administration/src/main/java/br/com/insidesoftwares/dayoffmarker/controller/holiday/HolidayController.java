package br.com.insidesoftwares.dayoffmarker.controller.holiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.holiday.HolidayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
    @InsideRequestPost(uri = "/v1/holiday", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<UUID> save(
            @JdempotentRequestPayload @RequestBody HolidayRequestDTO holidayRequestDTO
    ) {
        return holidayService.save(holidayRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
    @InsideRequestPost(uri = "/v1/holiday/batch", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<List<UUID>> saveInBatch(
            @JdempotentRequestPayload @RequestBody HolidayBatchRequestDTO holidayBatchRequestDTO
    ) {
        return holidayService.saveInBatch(holidayBatchRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
    @InsideRequestPut(uri = "/v1/holiday/{id}", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody HolidayRequestDTO holidayRequestDTO
    ) {
        holidayService.update(id, holidayRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
