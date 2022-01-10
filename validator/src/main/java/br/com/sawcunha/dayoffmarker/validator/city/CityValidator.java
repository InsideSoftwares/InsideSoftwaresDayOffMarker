package br.com.sawcunha.dayoffmarker.validator.city;

import br.com.sawcunha.dayoffmarker.commons.dto.request.CityRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.sawcunha.dayoffmarker.repository.CityRepository;
import br.com.sawcunha.dayoffmarker.repository.StateRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityValidator implements Validator<Long, CityRequestDTO> {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Override
    public void validator(CityRequestDTO cityRequestDTO) throws Exception {
        if(!stateRepository.existsById(cityRequestDTO.getStateID())) throw new StateNotExistException();
        if(
                cityRepository.existsByCodeAndAcronymAndStateID(
                        cityRequestDTO.getCode(),
                        cityRequestDTO.getAcronym(),
                        cityRequestDTO.getStateID()
                )
        ) throw new CityCodeAcronymStateExistException();
        if(
                cityRepository.existsByCodeAndStateID(
                        cityRequestDTO.getCode(),
                        cityRequestDTO.getStateID()
                )
        ) throw new CityCodeStateExistException();
        if(
                cityRepository.existsByNameAndStateID(
                        cityRequestDTO.getName(),
                        cityRequestDTO.getStateID()
                )
        ) throw new CityNameStateExistException();
    }

    @Override
    public void validator(Long cityID, CityRequestDTO cityRequestDTO) throws Exception {
        if(!cityRepository.existsById(cityID)) throw new CityNotExistException();
        if(!stateRepository.existsById(cityRequestDTO.getStateID())) throw new StateNotExistException();
        if(
                cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
                        cityRequestDTO.getCode(),
                        cityRequestDTO.getAcronym(),
                        cityRequestDTO.getStateID(),
                        cityID
                )
        ) throw new CityCodeAcronymStateExistException();
        if(
                cityRepository.existsByCodeAndStateIDAndNotId(
                        cityRequestDTO.getCode(),
                        cityRequestDTO.getStateID(),
                        cityID
                )
        ) throw new CityCodeStateExistException();
        if(
                cityRepository.existsByNameAndStateIDAndNotId(
                        cityRequestDTO.getName(),
                        cityRequestDTO.getStateID(),
                        cityID
                )
        ) throw new CityNameStateExistException();
    }


}
