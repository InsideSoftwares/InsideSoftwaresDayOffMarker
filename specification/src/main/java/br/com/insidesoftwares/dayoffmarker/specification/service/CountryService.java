package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCountry;
import br.com.insidesoftwares.dayoffmarker.entity.Country;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CountryService {

    InsideSoftwaresResponse<List<CountryResponseDTO>> findAll(final PaginationFilter<eOrderCountry> paginationFilter);

    InsideSoftwaresResponse<CountryResponseDTO> findById(final Long countryID) throws CountryNotExistException;

    void save(final @Valid CountryRequestDTO countryRequestDTO);
    void update(final Long countryID, final @Valid CountryRequestDTO countryRequestDTO);

    boolean validCountry(final String name);
    Country findCountryByName(final String name) throws CountryNameInvalidException;
    Country findCountryDefault();
    Country findCountryByNameOrDefault(final String name);
    Country findCountryByCountryId(final Long countryId);
    Country findCountryByCountryIdOrDefault(final Long countryId);
}
