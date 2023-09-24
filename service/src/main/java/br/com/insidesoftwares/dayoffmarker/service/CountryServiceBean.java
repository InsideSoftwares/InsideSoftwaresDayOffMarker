package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCountry;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.CountryMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.CountryService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
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
class CountryServiceBean implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final ConfigurationService configurationService;
    private final Validator<Long, CountryRequestDTO> countryValidator;

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
    @Transactional(rollbackFor = {CountryNameExistExpetion.class, CountryCodeExistExpetion.class, CountryAcronymExistExpetion.class})
    @Override
    public InsideSoftwaresResponseDTO<Long> save(final @Valid CountryRequestDTO countryRequestDTO) {
        countryValidator.validator(countryRequestDTO);

        Country country = Country.builder()
                .name(countryRequestDTO.name())
                .code(countryRequestDTO.code())
                .acronym(countryRequestDTO.acronym())
                .build();

        country = countryRepository.save(country);

        return InsideSoftwaresResponseDTO.<Long>builder().data(country.getId()).build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {CountryNameExistExpetion.class, CountryCodeExistExpetion.class, CountryAcronymExistExpetion.class})
    @Override
    public void update(
            final Long countryID,
            final @Valid CountryRequestDTO countryRequestDTO
    ) {
        countryValidator.validator(countryID, countryRequestDTO);

        Country country = countryRepository.getReferenceById(countryID);
        country.setCode(countryRequestDTO.code());
        country.setAcronym(countryRequestDTO.acronym());
        country.setName(countryRequestDTO.name());

        countryRepository.save(country);
    }

    @InsideAudit
    @Override
    public Country findCountryDefault() {
        String countryDefault = configurationService.findValueConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);
        return findCountryByNameOrDefault(countryDefault);
    }

    @InsideAudit
    @Override
    public Country findCountryByNameOrDefault(final String name) {
        String nameCountry = Objects.nonNull(name) ? name :
                configurationService.findValueConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);

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
