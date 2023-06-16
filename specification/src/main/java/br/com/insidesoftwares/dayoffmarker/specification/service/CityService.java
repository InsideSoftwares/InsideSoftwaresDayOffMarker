package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.entity.city.City;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CityService {

    InsideSoftwaresResponse<List<CityResponseDTO>> findAll(
            final Long stateID,
            final PaginationFilter<eOrderCity> paginationFilter
    );

    InsideSoftwaresResponse<CityResponseDTO> findById(final Long cityID);

    void save(final @Valid CityRequestDTO cityRequestDTO);
    void update(final Long cityID, final @Valid CityRequestDTO cityRequestDTO);

	void addCityHoliday(final Long cityID, final @Valid CityHolidayRequestDTO cityHolidayRequestDTO);
	void deleteCityHoliday(final Long cityID, final @Valid CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO);

	City findCityById(final Long cityID);
	City findCityFullHolidayById(final Long cityID);

}
