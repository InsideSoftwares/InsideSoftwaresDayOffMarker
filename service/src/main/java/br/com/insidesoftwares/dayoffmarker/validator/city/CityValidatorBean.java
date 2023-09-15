package br.com.insidesoftwares.dayoffmarker.validator.city;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CityValidatorBean implements Validator<Long, CityRequestDTO> {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Override
    public void validator(final CityRequestDTO cityRequestDTO) {
        if(!stateRepository.existsById(cityRequestDTO.stateID())) throw new StateNotExistException();
        if(
                cityRepository.existsByCodeAndAcronymAndStateID(
                        cityRequestDTO.code(),
                        cityRequestDTO.acronym(),
                        cityRequestDTO.stateID()
                )
        ) throw new CityCodeAcronymStateExistException();
        if(
                cityRepository.existsByCodeAndStateID(
                        cityRequestDTO.code(),
                        cityRequestDTO.stateID()
                )
        ) throw new CityCodeStateExistException();
        if(
                cityRepository.existsByNameAndStateID(
                        cityRequestDTO.name(),
                        cityRequestDTO.stateID()
                )
        ) throw new CityNameStateExistException();
    }

    @Override
    public void validator(final Long cityID, final CityRequestDTO cityRequestDTO) {
        if(!cityRepository.existsById(cityID)) throw new CityNotExistException();
        if(!stateRepository.existsById(cityRequestDTO.stateID())) throw new StateNotExistException();
        if(
                cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
                        cityRequestDTO.code(),
                        cityRequestDTO.acronym(),
                        cityRequestDTO.stateID(),
                        cityID
                )
        ) throw new CityCodeAcronymStateExistException();
        if(
                cityRepository.existsByCodeAndStateIDAndNotId(
                        cityRequestDTO.code(),
                        cityRequestDTO.stateID(),
                        cityID
                )
        ) throw new CityCodeStateExistException();
        if(
                cityRepository.existsByNameAndStateIDAndNotId(
                        cityRequestDTO.name(),
                        cityRequestDTO.stateID(),
                        cityID
                )
        ) throw new CityNameStateExistException();
    }

	@Override
	public void validator(final Long cityID) {
		if(!cityRepository.existsById(cityID)) throw new CityNotExistException();
	}


}
