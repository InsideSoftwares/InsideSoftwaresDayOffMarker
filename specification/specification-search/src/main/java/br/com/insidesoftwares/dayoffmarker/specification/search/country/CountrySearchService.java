package br.com.insidesoftwares.dayoffmarker.specification.search.country;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface CountrySearchService {
    InsideSoftwaresResponseDTO<List<CountryResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<CountryResponseDTO> findById(final UUID countryID);

    Country findCountryByNameOrDefault(final String name);

    Country findCountryByCountryId(final UUID countryId);

    Country findCountryDefault();
}
