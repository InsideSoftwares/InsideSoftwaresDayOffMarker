package br.com.insidesoftwares.dayoffmarker.validator.state;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class StateValidatorBean implements Validator<Long, StateRequestDTO> {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Override
    public void validator(final StateRequestDTO stateRequestDTO) {
        if(!countryRepository.existsById(stateRequestDTO.countryId())) throw new CountryNotExistException();
        if(
                stateRepository.existsByNameAndCountryIdAndAcronym(
                        stateRequestDTO.name(),
                        stateRequestDTO.countryId(),
                        stateRequestDTO.acronym()
                )
        ) throw new StateNameCountryAcronymExistException();
        if(
                stateRepository.existsByCountryIdAndAcronym(
                        stateRequestDTO.countryId(),
                        stateRequestDTO.acronym()
                )
        ) throw new StateCountryAcronymExistException();

    }

    @Override
    public void validator(final Long stateId, final StateRequestDTO stateRequestDTO) {
        if(!stateRepository.existsById(stateId)) throw new StateNotExistException();
        if(!countryRepository.existsById(stateRequestDTO.countryId())) throw new CountryNotExistException();
        if(
                stateRepository.existsByNameAndCountryIdAndAcronymAndNotId(
                        stateRequestDTO.name(),
                        stateRequestDTO.countryId(),
                        stateRequestDTO.acronym(),
                        stateId
                )
        ) throw new StateNameCountryAcronymExistException();
        if(
                stateRepository.existsByCountryIdAndAcronymAndNotId(
                        stateRequestDTO.countryId(),
                        stateRequestDTO.acronym(),
                        stateId
                )
        ) throw new StateCountryAcronymExistException();
    }

	@Override
	public void validator(final Long stateId) {
		if(!stateRepository.existsById(stateId)) throw new StateNotExistException();
	}
}
