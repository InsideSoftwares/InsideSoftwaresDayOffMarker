package br.com.insidesoftwares.dayoffmarker.controller.day;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderDay;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
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
import java.time.Month;
import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class DayController {

	private final DayService dayService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<List<DayDTO>> findAll(
		@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
		PaginationFilter<eOrderDay> paginationFilter
	) {
		return dayService.getAllDays(startDate, endDate, paginationFilter);
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Day.Write')")
	@InsideRequestPost(uri = "/v1/day/{id}/link-tags", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_LINK_TAG", ttl = 1)
	public InsideSoftwaresResponse<Void> linkTag(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody LinkTagRequestDTO linkTagRequestDTO
	) {
		dayService.linkTag(id, linkTagRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Day.Write')")
	@InsideRequestPost(uri = "/v1/day/{id}/unlink-tags", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_UNLINK_TAG", ttl = 1)
	public InsideSoftwaresResponse<Void> unlinkTag(
			@JdempotentRequestPayload @PathVariable Long id,
			@JdempotentRequestPayload @RequestBody LinkTagRequestDTO linkTagRequestDTO
	) {
		dayService.unlinkTag(id, linkTagRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<DayDTO> findById(@PathVariable final Long id) {
		return dayService.getDayByID(id);
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day/date/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<DayDTO> findDayByDate(
		@PathVariable final LocalDate date,
		@RequestParam(value = "tagID", required = false) Long tagID,
		@RequestParam(value = "tagCode", required = false) String tagCode
	) {
		return dayService.getDayByDate(date, tagID, tagCode);
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day/tag/{tagID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<List<DayDTO>> findDaysByTag(
		@PathVariable final Long tagID
	) {
		return dayService.getDaysByTag(tagID);
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day/tag/code/{codeTag}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<List<DayDTO>> findDaysByTag(
		@PathVariable final String codeTag
	) {
		return dayService.getDaysByTag(codeTag);
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day/current/month", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<List<DayDTO>> findDaysOfCurrentMonth() {
		return dayService.getDaysOfCurrentMonth();
	}

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
	@InsideRequestGet(uri = "/v1/day/month/{month}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
	public InsideSoftwaresResponse<List<DayDTO>> findDaysOfMonth(
		@PathVariable final Month month,
		@RequestParam(value = "year", required = false) Integer year
	) {
		return dayService.getDaysOfMonth(month, year);
	}

}
