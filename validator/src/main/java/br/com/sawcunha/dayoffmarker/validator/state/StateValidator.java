package br.com.sawcunha.dayoffmarker.validator.state;

import br.com.sawcunha.dayoffmarker.commons.dto.request.StateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.repository.StateRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StateValidator implements Validator<Long, StateRequestDTO> {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    @Override
    public void validator(StateRequestDTO stateRequestDTO) throws DayOffMarkerGenericException {
        if(!countryRepository.existsById(stateRequestDTO.getCountryId())) throw new CountryNotExistException();
        if(
                stateRepository.existsByNameAndCountryIdAndAcronym(
                        stateRequestDTO.getName(),
                        stateRequestDTO.getCountryId(),
                        stateRequestDTO.getAcronym()
                )
        ) throw new StateNameCountryAcronymExistException();
        if(
                stateRepository.existsByCountryIdAndAcronym(
                        stateRequestDTO.getCountryId(),
                        stateRequestDTO.getAcronym()
                )
        ) throw new StateCountryAcronymExistException();

    }

    @Transactional(readOnly = true)
    @Override
    public void validator(Long stateId, StateRequestDTO stateRequestDTO) throws DayOffMarkerGenericException {
        if(!countryRepository.existsById(stateRequestDTO.getCountryId())) throw new CountryNotExistException();
        if(!stateRepository.existsById(stateId)) throw new StateNotExistException();
        if(
                stateRepository.existsByNameAndCountryIdAndAcronymAndNotId(
                        stateRequestDTO.getName(),
                        stateRequestDTO.getCountryId(),
                        stateRequestDTO.getAcronym(),
                        stateId
                )
        ) throw new StateNameCountryAcronymExistException();
        if(
                stateRepository.existsByCountryIdAndAcronymAndNotId(
                        stateRequestDTO.getCountryId(),
                        stateRequestDTO.getAcronym(),
                        stateId
                )
        ) throw new StateCountryAcronymExistException();
    }
}
