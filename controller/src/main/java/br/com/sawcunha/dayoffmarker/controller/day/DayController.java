package br.com.sawcunha.dayoffmarker.controller.day;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
		value = "/api",
		produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class DayController {

	private final DayService dayService;

	@PostMapping(value = "/v1/day/{id}/link-tags", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_TAG", allEntries=true)
	public DayOffMarkerResponse<Void> linkTag(
			@PathVariable Long id,
			@RequestBody LinkTagRequestDTO linkTagRequestDTO
	) throws DayOffMarkerGenericException {
		dayService.linkTag(id, linkTagRequestDTO);
		return DayOffMarkerResponse.<Void>builder().build();
	}

	@PostMapping(value = "/v1/day/{id}/unlink-tags", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_TAG", allEntries=true)
	public DayOffMarkerResponse<Void> unlinkTag(
			@PathVariable Long id,
			@RequestBody LinkTagRequestDTO linkTagRequestDTO
	) throws DayOffMarkerGenericException {
		dayService.unlinkTag(id, linkTagRequestDTO);
		return DayOffMarkerResponse.<Void>builder().build();
	}

}
