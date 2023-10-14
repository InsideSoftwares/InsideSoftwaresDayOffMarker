package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCountry;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.CountryMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.ConfigurationSearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.CountrySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CountrySearchServiceBean implements CountrySearchService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final ConfigurationSearchService configurationSearchService;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<CountryResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter) {
        Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderCountry.ID);

        Page<Country> countries = countryRepository.findAll(pageable);
        return InsideSoftwaresResponseDTO.<List<CountryResponseDTO>>builder()
                .data(countryMapper.toDTOs(countries.getContent()))
                .insidePaginatedDTO(
                        PaginationUtils.createPaginated(
                                countries.getTotalPages(),
                                countries.getTotalElements(),
                                countries.getContent().size(),
                                paginationFilter.getSizePerPage()
                        )
                )
                .build();
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<CountryResponseDTO> findById(final Long countryID) throws CountryNotExistException {
        Country country = findCountryByCountryId(countryID);

        return InsideSoftwaresResponseDTO.<CountryResponseDTO>builder()
                .data(countryMapper.toDTO(country))
                .build();
    }

    @InsideAudit
    @Override
    public Country findCountryDefault() {
        String countryDefault = configurationSearchService.findValueConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);
        return findCountryByNameOrDefault(countryDefault);
    }

    @InsideAudit
    @Override
    public Country findCountryByNameOrDefault(final String name) {
        String nameCountry = Objects.nonNull(name) ? name :
                configurationSearchService.findValueConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);

        Optional<Country> countryOptional = countryRepository.findCountryByName(nameCountry);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

    @InsideAudit
    @Override
    public Country findCountryByCountryId(final Long countryId) {
        Optional<Country> countryOptional = countryRepository.findById(countryId);
        return countryOptional.orElseThrow(CountryNotExistException::new);
    }

}
