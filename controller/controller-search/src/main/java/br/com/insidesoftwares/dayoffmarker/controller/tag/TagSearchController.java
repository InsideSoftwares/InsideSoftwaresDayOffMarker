package br.com.insidesoftwares.dayoffmarker.controller.tag;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.search.tag.TagSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class TagSearchController {

    private final TagSearchService tagService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Tag.Read')")
    @InsideRequestGet(uri = "/v1/tag", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_TAG")
    public InsideSoftwaresResponseDTO<List<TagResponseDTO>> findAll(
            InsidePaginationFilterDTO paginationFilter
    ) {
        return tagService.findAll(paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Tag.Read')")
    @InsideRequestGet(uri = "/v1/tag/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_TAG")
    public InsideSoftwaresResponseDTO<TagResponseDTO> findById(@PathVariable UUID id) {
        return tagService.findById(id);
    }

}
