package br.com.insidesoftwares.dayoffmarker.controller.tag;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.TagService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@InsideSoftwaresController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
    @InsideRequestPost(uri = "/v1/tag", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<Long> save(
            @JdempotentRequestPayload @RequestBody TagRequestDTO tagRequestDTO
    ) {
        return tagService.save(tagRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
    @InsideRequestPost(uri = "/v1/tag/{id}", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
            @JdempotentRequestPayload @PathVariable Long id,
            @JdempotentRequestPayload @RequestBody TagRequestDTO tagRequestDTO
    ) {
        tagService.update(id, tagRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
    @InsideRequestPost(uri = "/v1/tag/link/day", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<TagLinkResponseDTO> linkTagByDay(
            @JdempotentRequestPayload @RequestBody TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO
    ) {
        return tagService.linkTagByDay(tagLinkUnlinkRequestDTO);
    }

    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
    @InsideRequestPost(uri = "/v1/tag/unlink/day", httpCode = HttpStatus.CREATED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<TagLinkResponseDTO> unlinkTagByDay(
            @JdempotentRequestPayload @RequestBody TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO
    ) {
        return tagService.unlinkTagByDay(tagLinkUnlinkRequestDTO);
    }

}
