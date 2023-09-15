package br.com.insidesoftwares.dayoffmarker.controller.fixedholiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderFixedHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
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

@Tag(name = "Fixed Holiday", description = "Get and register the fixed holidays")
@InsideSoftwaresController
@RequiredArgsConstructor
public class FixedHolidayController {

    private final FixedHolidayService fixedHolidayService;

	@Operation(
		summary = "Get All Fixed Holidays",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.FixedHoliday.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.FixedHoliday.Read')")
	@InsideRequestGet(uri = "/v1/fixed-holiday", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_FIXEDHOLIDAY")
    public InsideSoftwaresResponseDTO<List<FixedHolidayResponseDTO>> findAll(
			InsidePaginationFilterDTO<eOrderFixedHoliday> paginationFilter
    ) {
        return fixedHolidayService.findAll(paginationFilter);
    }

	@Operation(
		summary = "Get Fixed Holiday by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.FixedHoliday.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.FixedHoliday.Read')")
	@InsideRequestGet(uri = "/v1/fixed-holiday/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_FIXEDHOLIDAY")
    public InsideSoftwaresResponseDTO<FixedHolidayResponseDTO> findById(@PathVariable Long id) throws FixedHolidayNotExistException {
        return fixedHolidayService.findById(id);
    }

	@Operation(
		summary = "Create Fixed Holiday",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.FixedHoliday.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
	@InsideRequestPost(uri = "/v1/fixed-holiday", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> save(
		@JdempotentRequestPayload @RequestBody FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) {
        fixedHolidayService.save(fixedHolidayRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

	@Operation(
		summary = "Update Fixed Holiday by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.FixedHoliday.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
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
