package br.com.sawcunha.dayoffmarker.controller.holiday;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.specification.service.HolidayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/v1/holiday")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Cacheable("DAYOFF_MARKER_HOLIDAY")
    public DayOffMarkerResponse<List<HolidayResponseDTO>> findAll(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "country", required = false) String nameCountry,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sizePerPage", required = false, defaultValue = "10") int sizePerPage,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "DAY") eOrderHoliday orderHoliday
    ) throws DayOffMarkerGenericException {
        return holidayService.findAll(startDate, endDate, nameCountry, page, sizePerPage, direction, orderHoliday);
    }

    @GetMapping("/v1/holiday/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Cacheable("DAYOFF_MARKER_HOLIDAY")
    public DayOffMarkerResponse<HolidayResponseDTO> findById(@PathVariable Long id) throws DayOffMarkerGenericException {
        return holidayService.findById(id);
    }

    @PostMapping(value = "/v1/holiday", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_HOLIDAY", allEntries=true)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_HOLIDAY", ttl = 1, ttlTimeUnit = TimeUnit.DAYS)
    public DayOffMarkerResponse<HolidayResponseDTO> save(
            @JdempotentRequestPayload @RequestBody HolidayRequestDTO holidayRequestDTO
    ) throws DayOffMarkerGenericException {
        return holidayService.save(holidayRequestDTO);
    }

    @PutMapping(value = "/v1/holiday/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMIN')")
	@CacheEvict(value="DAYOFF_MARKER_HOLIDAY", allEntries=true)
    public DayOffMarkerResponse<HolidayResponseDTO> update(
            @PathVariable Long id,
            @RequestBody HolidayRequestDTO holidayRequestDTO
    ) throws DayOffMarkerGenericException {
        return holidayService.update(id, holidayRequestDTO);
    }

}
