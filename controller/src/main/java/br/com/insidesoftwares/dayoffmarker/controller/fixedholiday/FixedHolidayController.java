package br.com.insidesoftwares.dayoffmarker.controller.fixedholiday;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderFixedHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
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
public class FixedHolidayController {

    private final FixedHolidayService fixedHolidayService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.FixedHoliday.Read')")
	@InsideRequestGet(uri = "/v1/fixed-holiday", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_FIXEDHOLIDAY")
    public InsideSoftwaresResponse<List<FixedHolidayResponseDTO>> findAll(
			PaginationFilter<eOrderFixedHoliday> paginationFilter
    ) {
        return fixedHolidayService.findAll(paginationFilter);
    }

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.FixedHoliday.Read')")
	@InsideRequestGet(uri = "/v1/fixed-holiday/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_FIXEDHOLIDAY")
    public InsideSoftwaresResponse<FixedHolidayResponseDTO> findById(@PathVariable Long id) throws FixedHolidayNotExistException {
        return fixedHolidayService.findById(id);
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
	@InsideRequestPost(uri = "/v1/fixed-holiday", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponse<Void> save(
		@JdempotentRequestPayload @RequestBody FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) {
        fixedHolidayService.save(fixedHolidayRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.FixedHoliday.Write')")
	@InsideRequestPut(uri = "/v1/fixed-holiday/{id}", httpCode = HttpStatus.ACCEPTED, nameCache = {"DAYOFF_MARKER_FIXEDHOLIDAY"})
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_FIXEDHOLIDAY", ttl = 1)
    public InsideSoftwaresResponse<Void> update(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO
    ) {
        fixedHolidayService.update(id, fixedHolidayRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

}
