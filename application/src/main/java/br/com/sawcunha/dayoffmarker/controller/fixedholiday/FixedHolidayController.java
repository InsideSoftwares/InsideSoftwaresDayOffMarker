package br.com.sawcunha.dayoffmarker.controller.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderFixedHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.specification.service.FixedHolidayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
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
public class FixedHolidayController {

    private final FixedHolidayService fixedHolidayService;

    @GetMapping("/v1/fixed-holiday")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Cacheable("DAYOFF_MARKER_ADMIN")
    public DayOffMarkerResponse<List<FixedHolidayResponseDTO>> findAll(
            @RequestParam(value = "country", required = false) String nameCountry,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sizePerPage", required = false, defaultValue = "10") int sizePerPage,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "ID") eOrderFixedHoliday orderFixedHoliday
    ) throws Exception {
        return fixedHolidayService.findAll(nameCountry, page, sizePerPage, direction, orderFixedHoliday);
    }

    @GetMapping("/v1/fixed-holiday/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Cacheable("DAYOFF_MARKER_ADMIN")
    public DayOffMarkerResponse<FixedHolidayResponseDTO> findById(@PathVariable Long id) throws FixedHolidayNotExistException {
        return fixedHolidayService.findById(id);
    }

    @PostMapping(value = "/v1/fixed-holiday", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @JdempotentResource(cachePrefix = "dayoff_marker", ttl = 1, ttlTimeUnit = TimeUnit.DAYS)
    public DayOffMarkerResponse<FixedHolidayResponseDTO> save(
            @JdempotentRequestPayload @RequestBody FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) throws Exception {
        return fixedHolidayService.save(fixedHolidayRequestDTO);
    }

    @PutMapping(value = "/v1/fixed-holiday/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<FixedHolidayResponseDTO> update(
            @PathVariable Long id,
            @RequestBody FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) throws Exception {
        return fixedHolidayService.update(id, fixedHolidayRequestDTO);
    }

}
