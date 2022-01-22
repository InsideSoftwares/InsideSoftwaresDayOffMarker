package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderCountry;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.mapper.CountryMapper;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceBean implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final ConfigurationServiceBean configurationService;
    private final Validator<Long, CountryRequestDTO> countryValidator;

    @Override
    public DayOffMarkerResponse<List<CountryResponseDTO>> findAll(
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderCountry orderCountry
    ) {
        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderCountry);

        Page<Country> countries = countryRepository.findAll(pageable);
        return DayOffMarkerResponse.<List<CountryResponseDTO>>builder()
                .data(countryMapper.toDTOs(countries.getContent()))
                .paginated(
                        PaginationUtils.createPaginated(
                                countries.getTotalPages(),
                                countries.getTotalElements(),
                                sizePerPage
                        )
                )
                .build();
    }

    @Override
    public DayOffMarkerResponse<CountryResponseDTO> findById(final Long countryID) throws CountryNotExistException {
        Country country = countryRepository
                .findById(countryID)
                .orElseThrow(CountryNotExistException::new);

        return DayOffMarkerResponse.<CountryResponseDTO>builder()
                .data(countryMapper.toDTO(country))
                .build();
    }

    @Transactional(rollbackFor = {CountryNameExistExpetion.class,CountryCodeExistExpetion.class,CountryAcronymExistExpetion.class})
    @Override
    public DayOffMarkerResponse<CountryResponseDTO> save(final @Valid CountryRequestDTO countryRequestDTO) throws DayOffMarkerGenericException {
        countryValidator.validator(countryRequestDTO);

        Country country = Country.builder()
                .name(countryRequestDTO.getName())
                .code(countryRequestDTO.getCode())
                .acronym(countryRequestDTO.getAcronym())
                .build();

        country = countryRepository.save(country);
        return DayOffMarkerResponse.<CountryResponseDTO>builder()
                .data(countryMapper.toDTO(country))
                .build();
    }

    @Transactional(rollbackFor = {CountryNameExistExpetion.class,CountryCodeExistExpetion.class,CountryAcronymExistExpetion.class})
    @Override
    public DayOffMarkerResponse<CountryResponseDTO> update(
            final Long countryID,
            final @Valid CountryRequestDTO countryRequestDTO
    ) throws DayOffMarkerGenericException {
        countryValidator.validator(countryID, countryRequestDTO);

        Country country = countryRepository.getById(countryID);
        country.setCode(countryRequestDTO.getCode());
        country.setAcronym(countryRequestDTO.getAcronym());
        country.setName(countryRequestDTO.getName());
        country = countryRepository.save(country);
        return DayOffMarkerResponse.<CountryResponseDTO>builder()
                .data(countryMapper.toDTO(country))
                .build();
    }

    @Override
    public boolean validCountry(final String name) {
        Optional<Country> countryOptional = countryRepository.findCountryByName(name);
        return countryOptional.isPresent();
    }

    @Override
    public Country findCountryByName(final String name) throws CountryNameInvalidException {
        Optional<Country> countryOptional = countryRepository.findCountryByName(name);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

    @Override
    public Country findCountryDefault() throws DayOffMarkerGenericException {
        String countryDefault = configurationService.findValueConfigurationByKey(eConfigurationkey.COUNTRY_DEFAULT);
        Optional<Country> countryOptional = countryRepository.findCountryByName(countryDefault);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

    @Override
    public Country findCountryByNameOrDefault(final String name) throws DayOffMarkerGenericException {
        String nameCountry = Objects.nonNull(name) ?
                name :
                configurationService.findValueConfigurationByKey(eConfigurationkey.COUNTRY_DEFAULT);

        Optional<Country> countryOptional = countryRepository.findCountryByName(nameCountry);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

    @Override
    public Country findCountryByCountryId(Long countryId) throws DayOffMarkerGenericException {
        Optional<Country> countryOptional = countryRepository.findById(countryId);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

}
