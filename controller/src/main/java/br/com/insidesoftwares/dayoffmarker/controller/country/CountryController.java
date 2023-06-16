package br.com.insidesoftwares.dayoffmarker.controller.country;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCountry;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.specification.service.CountryService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

@InsideSoftwaresController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Country.Read')")
	@InsideRequestGet(uri = "/v1/country", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_COUNTRY")
    public InsideSoftwaresResponse<List<CountryResponseDTO>> findAll(
		PaginationFilter<eOrderCountry> paginationFilter
    ) {
        return countryService.findAll(paginationFilter);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Country.Read')")
	@InsideRequestGet(uri = "/v1/country/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_COUNTRY")
    public InsideSoftwaresResponse<CountryResponseDTO> findById(@PathVariable Long id) throws CountryNotExistException {
        return countryService.findById(id);
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Country.Write')")
	@InsideRequestPost(uri = "/v1/country", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_COUNTRY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"}
	)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_COUNTRY", ttl = 1, ttlTimeUnit = TimeUnit.DAYS)
    public InsideSoftwaresResponse<Void> save(
            @JdempotentRequestPayload @RequestBody CountryRequestDTO countryRequestDTO
    ) {
        countryService.save(countryRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Country.Write')")
	@InsideRequestPut(uri = "/v1/country/{id}", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_COUNTRY", "DAYOFF_MARKER_CITY", "DAYOFF_MARKER_STATE"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_COUNTRY", ttl = 1)
    public InsideSoftwaresResponse<Void> update(
            @JdempotentRequestPayload @PathVariable Long id,
            @JdempotentRequestPayload @RequestBody CountryRequestDTO countryRequestDTO
    ) {
        countryService.update(id, countryRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

}
