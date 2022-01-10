package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderCountry;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.entity.Country;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface CountryService {

    DayOffMarkerResponse<List<CountryResponseDTO>> findAll(
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderCountry orderCountry
    );

    DayOffMarkerResponse<CountryResponseDTO> findById(final Long countryID) throws CountryNotExistException;

    DayOffMarkerResponse<CountryResponseDTO> save(final @Valid CountryRequestDTO countryRequestDTO) throws Exception;
    DayOffMarkerResponse<CountryResponseDTO> update(final Long countryID, final @Valid CountryRequestDTO countryRequestDTO) throws Exception;

    boolean validCountry(final String name);
    Country findCountryByName(final String name) throws CountryNameInvalidException;
    Country findCountryDefault() throws Exception;
    Country findCountryByNameOrDefault(final String name) throws Exception;
    Country findCountryByCountryId(final Long countryId) throws Exception;
}
