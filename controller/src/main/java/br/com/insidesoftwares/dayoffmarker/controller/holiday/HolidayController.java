package br.com.insidesoftwares.dayoffmarker.controller.holiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Holiday.Read')")
	@InsideRequestGet(uri = "/v1/holiday", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_HOLIDAY")
    public InsideSoftwaresResponse<List<HolidayResponseDTO>> findAll(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
			PaginationFilter<eOrderHoliday> paginationFilter
    ) {
        return holidayService.findAll(startDate, endDate, paginationFilter);
    }

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Holiday.Read')")
	@InsideRequestGet(uri = "/v1/holiday/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_HOLIDAY")
    public InsideSoftwaresResponse<HolidayResponseDTO> findById(@PathVariable Long id) {
        return holidayService.findById(id);
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
	@InsideRequestPost(uri = "/v1/holiday", httpCode = HttpStatus.CREATED,
		nameCache = { "DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE" })
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponse<Void> save(
            @JdempotentRequestPayload @RequestBody HolidayRequestDTO holidayRequestDTO
    ) {
        holidayService.save(holidayRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
	@InsideRequestPost(uri = "/v1/holiday/batch", httpCode = HttpStatus.CREATED,
		nameCache = { "DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING" })
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
	public InsideSoftwaresResponse<Void> saveInBatch(
		@JdempotentRequestPayload @RequestBody HolidayBatchRequestDTO holidayBatchRequestDTO
	) {
		holidayService.saveInBatch(holidayBatchRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Holiday.Write')")
	@InsideRequestPut(uri = "/v1/holiday/{id}", httpCode = HttpStatus.CREATED,
		nameCache = { "DAYOFF_MARKER_HOLIDAY", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE", "DAYOFF_MARKER_WORKING" })
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1)
    public InsideSoftwaresResponse<Void> update(
            @PathVariable Long id,
            @RequestBody HolidayRequestDTO holidayRequestDTO
    ) {
        holidayService.update(id, holidayRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

}
