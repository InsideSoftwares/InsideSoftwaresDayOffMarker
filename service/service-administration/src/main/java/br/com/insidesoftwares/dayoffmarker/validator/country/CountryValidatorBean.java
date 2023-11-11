package br.com.insidesoftwares.dayoffmarker.validator.country;

import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CountryValidatorBean implements Validator<UUID, CountryRequestDTO> {

    private final CountryRepository countryRepository;

    @Override
    public void validator(final CountryRequestDTO countryRequestDTO) {
        if (countryRepository.existsByName(countryRequestDTO.name())) throw new CountryNameExistExpetion();
        if (countryRepository.existsByCode(countryRequestDTO.code())) throw new CountryCodeExistExpetion();
        if (countryRepository.existsByAcronym(countryRequestDTO.acronym())) throw new CountryAcronymExistExpetion();
    }

    @Override
    public void validator(final UUID countryId, final CountryRequestDTO countryRequestDTO) {
        if (!countryRepository.existsById(countryId)) throw new CountryNotExistException();
        if (countryRepository.existsByNameAndNotId(countryRequestDTO.name(), countryId))
            throw new CountryNameExistExpetion();
        if (countryRepository.existsByCodeAndNotId(countryRequestDTO.code(), countryId))
            throw new CountryCodeExistExpetion();
        if (countryRepository.existsByAcronymAndNotId(countryRequestDTO.acronym(), countryId))
            throw new CountryAcronymExistExpetion();
    }

    @Override
    public void validator(final UUID countryId) {
        if (!countryRepository.existsById(countryId)) throw new CountryNotExistException();

    }
}
