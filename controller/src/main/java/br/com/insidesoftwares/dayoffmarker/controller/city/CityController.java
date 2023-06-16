package br.com.insidesoftwares.dayoffmarker.controller.city;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestDelete;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPut;
import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.specification.service.CityService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@InsideSoftwaresController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.City.Read')")
	@InsideRequestGet(uri = "/v1/city", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_CITY")
	public InsideSoftwaresResponse<List<CityResponseDTO>> findAll(
		@RequestParam(value = "stateID", required = false) Long stateID,
		PaginationFilter<eOrderCity> paginationFilter
    ) {
        return cityService.findAll(stateID, paginationFilter);
    }

	@PreAuthorize("hasAnyRole('DayOff.Read','DayOff.City.Read')")
	@InsideRequestGet(uri = "/v1/city/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_CITY")
    public InsideSoftwaresResponse<CityResponseDTO> findById(@PathVariable Long id) {
        return cityService.findById(id);
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestPost(uri = "/v1/city", httpCode = HttpStatus.CREATED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponse<Void> save(
            @JdempotentRequestPayload @RequestBody CityRequestDTO cityRequestDTO
    ) {
        cityService.save(cityRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestPut(uri = "/v1/city/{id}", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
    public InsideSoftwaresResponse<Void> update(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody CityRequestDTO cityRequestDTO
    ) {
        cityService.update(id, cityRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
    }

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestPost(uri = "/v1/city/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
	public InsideSoftwaresResponse<Void> addCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody CityHolidayRequestDTO cityHolidayRequestDTO
	) {
		cityService.addCityHoliday(id, cityHolidayRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}

	@PreAuthorize("hasAnyRole('DayOff.Write','DayOff.City.Write')")
	@InsideRequestDelete(uri = "/v1/city/{id}/holiday", httpCode = HttpStatus.ACCEPTED,
		nameCache = {"DAYOFF_MARKER_CITY", "DAYOFF_MARKER_WORKING"}
	)
	@JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_CITY", ttl = 1)
	public InsideSoftwaresResponse<Void> deleteCityHoliday(
		@JdempotentRequestPayload @PathVariable Long id,
		@JdempotentRequestPayload @RequestBody CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO
	) {
		cityService.deleteCityHoliday(id, cityHolidayDeleteRequestDTO);
		return InsideSoftwaresResponse.<Void>builder().build();
	}
}
