package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCountry;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.mapper.CountryMapper;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.CountryService;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.entity.Country;
import br.com.insidesoftwares.dayoffmarker.repository.CountryRepository;
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

    @Override
    public InsideSoftwaresResponse<List<CountryResponseDTO>> findAll(final PaginationFilter<eOrderCountry> paginationFilter) {
        Pageable pageable = PaginationUtils.createPageable(paginationFilter);

        Page<Country> countries = countryRepository.findAll(pageable);
        return InsideSoftwaresResponse.<List<CountryResponseDTO>>builder()
                .data(countryMapper.toDTOs(countries.getContent()))
                .paginatedDTO(
                        PaginationUtils.createPaginated(
							countries.getTotalPages(),
							countries.getTotalElements(),
							countries.getContent().size(),
							paginationFilter.getSizePerPage()
                        )
                )
                .build();
    }

    @Override
    public InsideSoftwaresResponse<CountryResponseDTO> findById(final Long countryID) throws CountryNotExistException {
        Country country = countryRepository
                .findById(countryID)
                .orElseThrow(CountryNotExistException::new);

        return InsideSoftwaresResponse.<CountryResponseDTO>builder()
                .data(countryMapper.toDTO(country))
                .build();
    }

    @Transactional(rollbackFor = {CountryNameExistExpetion.class, CountryCodeExistExpetion.class, CountryAcronymExistExpetion.class})
    @Override
    public void save(final @Valid CountryRequestDTO countryRequestDTO) {
        countryValidator.validator(countryRequestDTO);

        Country country = Country.builder()
                .name(countryRequestDTO.name())
                .code(countryRequestDTO.code())
                .acronym(countryRequestDTO.acronym())
                .build();

        countryRepository.save(country);
    }

    @Transactional(rollbackFor = {CountryNameExistExpetion.class,CountryCodeExistExpetion.class,CountryAcronymExistExpetion.class})
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
    public Country findCountryDefault() {
        String countryDefault = configurationService.findValueConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);
        Optional<Country> countryOptional = countryRepository.findCountryByName(countryDefault);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

    @Override
    public Country findCountryByNameOrDefault(final String name)  {
        String nameCountry = Objects.nonNull(name) ?
                name :
                configurationService.findValueConfigurationByKey(Configurationkey.COUNTRY_DEFAULT);

        Optional<Country> countryOptional = countryRepository.findCountryByName(nameCountry);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

    @Override
    public Country findCountryByCountryId(final Long countryId) {
        Optional<Country> countryOptional = countryRepository.findById(countryId);
        return countryOptional.orElseThrow(CountryNameInvalidException::new);
    }

	@Override
	public Country findCountryByCountryIdOrDefault(final Long countryId) {
		if(Objects.isNull(countryId)) {
			return findCountryDefault();
		}

		Optional<Country> countryOptional = countryRepository.findById(countryId);
		return countryOptional.orElseThrow(CountryNameInvalidException::new);
	}

}
