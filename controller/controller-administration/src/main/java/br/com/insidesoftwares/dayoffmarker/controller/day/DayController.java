package br.com.insidesoftwares.dayoffmarker.controller.day;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.day.DayService;
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
public class DayController {

    private final DayService dayService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Day.Write')")
    @InsideRequestPost(uri = "/v1/day/{id}/link-tags", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_LINK_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> linkTag(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody LinkTagRequestDTO linkTagRequestDTO
    ) {
        dayService.linkTag(id, linkTagRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Day.Write')")
    @InsideRequestPost(uri = "/v1/day/{id}/unlink-tags", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_UNLINK_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> unlinkTag(
            @JdempotentRequestPayload @PathVariable UUID id,
            @JdempotentRequestPayload @RequestBody LinkTagRequestDTO linkTagRequestDTO
    ) {
        dayService.unlinkTag(id, linkTagRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}