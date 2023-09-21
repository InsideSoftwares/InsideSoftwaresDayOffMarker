package br.com.insidesoftwares.dayoffmarker.controller.holiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Holiday", description = "Get and register the holidays")
@InsideSoftwaresController
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

	@Operation(
		summary = "Get All Holidays",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Holiday.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Holiday.Read')")
	@InsideRequestGet(uri = "/v1/holiday", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_HOLIDAY")
    public InsideSoftwaresResponseDTO<List<HolidayResponseDTO>> findAll(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
			InsidePaginationFilterDTO paginationFilter
    ) {
        return holidayService.findAll(startDate, endDate, paginationFilter);
    }

	@Operation(
		summary = "Get Holiday by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Holiday.Read"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Holiday.Read')")
	@InsideRequestGet(uri = "/v1/holiday/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_HOLIDAY")
    public InsideSoftwaresResponseDTO<HolidayResponseDTO> findById(@PathVariable Long id) {
        return holidayService.findById(id);
    }

	@Operation(
		summary = "Create Holiday",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Holiday.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
	@InsideRequestPost(uri = "/v1/holiday", httpCode = HttpStatus.CREATED,
		nameCache = { "DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE" })
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Long> save(
            @JdempotentRequestPayload @RequestBody HolidayRequestDTO holidayRequestDTO
    ) {
        return holidayService.save(holidayRequestDTO);
    }

	@Operation(
		summary = "Create Holiday in Batch",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Holiday.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
	@InsideRequestPost(uri = "/v1/holiday/batch", httpCode = HttpStatus.CREATED,
		nameCache = { "DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING" })
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
	public InsideSoftwaresResponseDTO<List<Long>> saveInBatch(
		@JdempotentRequestPayload @RequestBody HolidayBatchRequestDTO holidayBatchRequestDTO
	) {
		return holidayService.saveInBatch(holidayBatchRequestDTO);
	}

	@Operation(
		summary = "Update Holiday by Id",
		security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Holiday.Write"}),
		parameters = {
			@Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
			@Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
		}
	)
	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
	@InsideRequestPut(uri = "/v1/holiday/{id}", httpCode = HttpStatus.CREATED,
		nameCache = { "DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING" })
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> update(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody HolidayRequestDTO holidayRequestDTO
    ) {
        holidayService.update(id, holidayRequestDTO);
		return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

}
