package br.com.insidesoftwares.dayoffmarker.specification.search.city;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface CitySearchService {
    InsideSoftwaresResponseDTO<List<CityResponseDTO>> findAll(final UUID stateID, final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<CityResponseDTO> findById(final UUID cityID);

    City findCityById(final UUID cityID);

    City findCityFullHolidayById(final UUID cityID);
}
