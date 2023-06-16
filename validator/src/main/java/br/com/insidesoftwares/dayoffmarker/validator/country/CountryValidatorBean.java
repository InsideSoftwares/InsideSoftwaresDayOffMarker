package br.com.insidesoftwares.dayoffmarker.validator.country;

import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CountryValidatorBean implements Validator<Long, CountryRequestDTO> {

    private final CountryRepository countryRepository;

    @Override
    public void validator(final CountryRequestDTO countryRequestDTO) {
        if(countryRepository.existsByName(countryRequestDTO.name())) throw new CountryNameExistExpetion();
        if(countryRepository.existsByCode(countryRequestDTO.code())) throw new CountryCodeExistExpetion();
        if(countryRepository.existsByAcronym(countryRequestDTO.acronym())) throw new CountryAcronymExistExpetion();
    }

    @Override
    public void validator(final Long countryId, final CountryRequestDTO countryRequestDTO) {
        if(!countryRepository.existsById(countryId)) throw new CountryNotExistException();
        if(countryRepository.existsByNameAndNotId(countryRequestDTO.name(), countryId)) throw new CountryNameExistExpetion();
        if(countryRepository.existsByCodeAndNotId(countryRequestDTO.code(), countryId)) throw new CountryCodeExistExpetion();
        if(countryRepository.existsByAcronymAndNotId(countryRequestDTO.acronym(), countryId)) throw new CountryAcronymExistExpetion();
    }

	@Override
	public void validator(final Long countryId) {
		if(!countryRepository.existsById(countryId)) throw new CountryNotExistException();

	}
}
