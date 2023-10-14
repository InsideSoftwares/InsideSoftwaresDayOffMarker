package br.com.insidesoftwares.dayoffmarker.controller.fixedholiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@InsideSoftwaresController
@RequiredArgsConstructor
public class FixedHolidayController {

    private final FixedHolidayService fixedHolidayService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
    @InsideRequestPost(uri = "/v1/fixed-holiday", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Long> save(
            @JdempotentRequestPayload @RequestBody FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) {
        return fixedHolidayService.save(fixedHolidayRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
    @InsideRequestPut(uri = "/v1/fixed-holiday/{id}", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable Long id,
            @JdempotentRequestPayload @RequestBody FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO
    ) {
        fixedHolidayService.update(id, fixedHolidayRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
