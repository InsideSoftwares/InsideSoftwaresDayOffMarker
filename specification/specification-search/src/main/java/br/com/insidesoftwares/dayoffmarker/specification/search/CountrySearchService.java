package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CountrySearchService {
    InsideSoftwaresResponseDTO<List<CountryResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<CountryResponseDTO> findById(final Long countryID);

    Country findCountryByNameOrDefault(final String name);

    Country findCountryByCountryId(final Long countryId);

    Country findCountryDefault();
}
