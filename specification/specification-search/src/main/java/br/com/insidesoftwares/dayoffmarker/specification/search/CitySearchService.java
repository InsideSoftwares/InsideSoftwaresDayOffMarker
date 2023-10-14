package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CitySearchService {
    InsideSoftwaresResponseDTO<List<CityResponseDTO>> findAll(final Long stateID, final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<CityResponseDTO> findById(final Long cityID);

    City findCityById(final Long cityID);

    City findCityFullHolidayById(final Long cityID);
}
