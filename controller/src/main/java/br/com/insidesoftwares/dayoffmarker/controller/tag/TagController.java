package br.com.insidesoftwares.dayoffmarker.controller.tag;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderTag;
import br.com.insidesoftwares.dayoffmarker.specification.service.TagService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Tag.Read')")
	@InsideRequestGet(uri = "/v1/tag", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_TAG")
	public InsideSoftwaresResponse<List<TagResponseDTO>> findAll(
		PaginationFilter<eOrderTag> paginationFilter
	) {
		return tagService.findAll(paginationFilter);
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Tag.Read')")
	@InsideRequestGet(uri = "/v1/tag/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_TAG")
	public InsideSoftwaresResponse<TagResponseDTO> findById(@PathVariable Long id) {
		return tagService.findById(id);
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
	@InsideRequestPost(uri = "/v1/tag", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
	public InsideSoftwaresResponse<Void> save(
			@JdempotentRequestPayload @RequestBody TagRequestDTO tagRequestDTO
	) {
		tagService.save(tagRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
	@InsideRequestPost(uri = "/v1/tag/{id}", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
	public InsideSoftwaresResponse<Void> update(
			@PathVariable Long id,
			@RequestBody TagRequestDTO tagRequestDTO
	) {
		tagService.update(id, tagRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

}
