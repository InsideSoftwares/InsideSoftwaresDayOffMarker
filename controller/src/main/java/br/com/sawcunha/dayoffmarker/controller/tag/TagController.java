package br.com.sawcunha.dayoffmarker.controller.tag;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkDayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderTag;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.specification.service.TagService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(
		value = "/api",
		produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	@GetMapping("/v1/tag")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Cacheable("DAYOFF_MARKER_TAG")
	public DayOffMarkerResponse<List<TagResponseDTO>> findAll(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "sizePerPage", required = false, defaultValue = "10") int sizePerPage,
			@RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
			@RequestParam(value = "orderBy", required = false, defaultValue = "ID") eOrderTag orderTag
	) throws DayOffMarkerGenericException {
		return tagService.findAll(page, sizePerPage, direction, orderTag);
	}

	@GetMapping("/v1/tag/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Cacheable("DAYOFF_MARKER_TAG")
	public DayOffMarkerResponse<TagResponseDTO> findById(@PathVariable Long id) throws DayOffMarkerGenericException {
		return tagService.findById(id);
	}

	@PostMapping(value = "/v1/tag", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_TAG", allEntries=true)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1, ttlTimeUnit = TimeUnit.DAYS)
	public DayOffMarkerResponse<TagResponseDTO> save(
			@JdempotentRequestPayload @RequestBody TagRequestDTO tagRequestDTO
	) throws DayOffMarkerGenericException {
		return tagService.save(tagRequestDTO);
	}

	@PutMapping(value = "/v1/tag/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_TAG", allEntries=true)
	public DayOffMarkerResponse<TagResponseDTO> update(
			@PathVariable Long id,
			@RequestBody TagRequestDTO tagRequestDTO
	) throws DayOffMarkerGenericException {
		return tagService.update(id, tagRequestDTO);
	}

	@PostMapping(value = "/v1/tag/{id}/link-days", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_TAG", allEntries=true)
	public DayOffMarkerResponse<Void> linkDay(
			@PathVariable Long id,
			@RequestBody LinkDayRequestDTO linkDayRequestDTO,
			@RequestParam(value = "countryID", required = false, defaultValue = "0") Long countryID
	) throws DayOffMarkerGenericException {
		tagService.linkDay(id, linkDayRequestDTO, countryID);
		return DayOffMarkerResponse.<Void>builder().build();
	}

}
