package br.com.insidesoftwares.dayoffmarker.controller.tag;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderTag;
import br.com.insidesoftwares.dayoffmarker.specification.service.TagService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Tag", description = "Register tags to be assigned to the Day.")
@InsideSoftwaresController
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	@Operation(
		summary = "Get All Tag",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Tag.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Tag.Read')")
	@InsideRequestGet(uri = "/v1/tag", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_TAG")
	public InsideSoftwaresResponseDTO<List<TagResponseDTO>> findAll(
		InsidePaginationFilterDTO<eOrderTag> paginationFilter
	) {
		return tagService.findAll(paginationFilter);
	}

	@Operation(
		summary = "Get Tag by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Tag.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Tag.Read')")
	@InsideRequestGet(uri = "/v1/tag/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_TAG")
	public InsideSoftwaresResponseDTO<TagResponseDTO> findById(@PathVariable Long id) {
		return tagService.findById(id);
	}

	@Operation(
		summary = "Create Tag",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Tag.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
	@InsideRequestPost(uri = "/v1/tag", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
	public InsideSoftwaresResponseDTO<Void> save(
			@JdempotentRequestPayload @RequestBody TagRequestDTO tagRequestDTO
	) {
		tagService.save(tagRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
	}

	@Operation(
		summary = "Update Tag by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Tag.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
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

	@Operation(
		summary = "Performs the linking of Tags in batch according to the informed parameters",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Tag.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
	@InsideRequestPost(uri = "/v1/tag/link/day", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
	public InsideSoftwaresResponseDTO<TagLinkResponseDTO> linkTagByDay(
		@JdempotentRequestPayload @RequestBody TagLinkRequestDTO tagLinkRequestDTO
	) {
		return tagService.linkTagByDay(tagLinkRequestDTO);
	}

	@Operation(
		summary = "Performs the unlinking of Tags in batch according to the informed parameters",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Tag.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Tag.Write')")
	@InsideRequestPost(uri = "/v1/tag/unlink/day", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_TAG", ttl = 1)
	public InsideSoftwaresResponseDTO<TagLinkResponseDTO> unlinkTagByDay(
		@JdempotentRequestPayload @RequestBody TagLinkRequestDTO tagLinkRequestDTO
	) {
		return tagService.unlinkTagByDay(tagLinkRequestDTO);
	}

}
