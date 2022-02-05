package br.com.sawcunha.dayoffmarker.validator.country;

import br.com.sawcunha.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CountryValidatorBean implements Validator<Long, CountryRequestDTO> {

    private final CountryRepository countryRepository;

    @Override
    public void validator(final CountryRequestDTO countryRequestDTO) throws DayOffMarkerGenericException {
        if(countryRepository.existsByName(countryRequestDTO.getName())) throw new CountryNameExistExpetion();
        if(countryRepository.existsByCode(countryRequestDTO.getCode())) throw new CountryCodeExistExpetion();
        if(countryRepository.existsByAcronym(countryRequestDTO.getAcronym())) throw new CountryAcronymExistExpetion();
    }

    @Override
    public void validator(final Long countryId, final CountryRequestDTO countryRequestDTO) throws DayOffMarkerGenericException {
        if(!countryRepository.existsById(countryId)) throw new CountryNotExistException();
        if(countryRepository.existsByNameAndNotId(countryRequestDTO.getName(), countryId)) throw new CountryNameExistExpetion();
        if(countryRepository.existsByCodeAndNotId(countryRequestDTO.getCode(), countryId)) throw new CountryCodeExistExpetion();
        if(countryRepository.existsByAcronymAndNotId(countryRequestDTO.getAcronym(), countryId)) throw new CountryAcronymExistExpetion();
    }

	@Override
	public void validator(final Long countryId) throws DayOffMarkerGenericException {
		if(!countryRepository.existsById(countryId)) throw new CountryNotExistException();

	}
}
