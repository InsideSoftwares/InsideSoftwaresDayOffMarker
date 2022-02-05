package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.StateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderState;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.State;
import br.com.sawcunha.dayoffmarker.mapper.StateMapper;
import br.com.sawcunha.dayoffmarker.repository.StateRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.StateService;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class StateServiceBean implements StateService {

    private final StateRepository stateRepository;
    private final CountryService countryService;
    private final Validator<Long, StateRequestDTO> stateValidator;
    private final StateMapper stateMapper;

    @Override
    public DayOffMarkerResponse<List<StateResponseDTO>> findAll(
            final String nameCountry,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderState orderState
    ) throws DayOffMarkerGenericException {

        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderState);

        Country country = countryService.findCountryByNameOrDefault(nameCountry);

        Page<State> states = stateRepository.findAllByCountry(country, pageable);

        return DayOffMarkerResponse.<List<StateResponseDTO>>builder()
                .data(stateMapper.toDTOs(states.getContent()))
                .paginated(
                        PaginationUtils.createPaginated(
                            states.getTotalPages(),
                            states.getTotalElements(),
                            sizePerPage
                    )
                )
                .build();
    }

    @Override
    public DayOffMarkerResponse<StateResponseDTO> findById(final Long stateID) throws DayOffMarkerGenericException {
        State state = stateRepository.findById(stateID).orElseThrow(StateNotExistException::new);
        return DayOffMarkerResponse.<StateResponseDTO>builder()
                .data(stateMapper.toDTO(state))
                .build();
    }

    @Transactional(rollbackFor = {
            CountryNameInvalidException.class,
            StateNameCountryAcronymExistException.class,
            StateCountryAcronymExistException.class,
            StateNotExistException.class
    })
    @Override
    public DayOffMarkerResponse<StateResponseDTO> save(StateRequestDTO stateRequestDTO) throws DayOffMarkerGenericException {
        stateValidator.validator(stateRequestDTO);

        Country country = countryService.findCountryByCountryId(stateRequestDTO.getCountryId());

        State state = State.builder()
                .name(stateRequestDTO.getName())
                .acronym(stateRequestDTO.getAcronym())
                .country(country)
                .build();

        state = stateRepository.save(state);

        return DayOffMarkerResponse.<StateResponseDTO>builder()
                .data(stateMapper.toDTO(state))
                .build();
    }

    @Transactional(rollbackFor = {
            CountryNameInvalidException.class,
            StateNameCountryAcronymExistException.class,
            StateCountryAcronymExistException.class
    })
    @Override
    public DayOffMarkerResponse<StateResponseDTO> update(Long stateID, StateRequestDTO stateRequestDTO) throws DayOffMarkerGenericException {
        stateValidator.validator(stateID, stateRequestDTO);
        State state = stateRepository.getById(stateID);

        if(!stateRequestDTO.getCountryId().equals(state.getCountry().getId())){
            Country country = countryService.findCountryByCountryId(stateRequestDTO.getCountryId());
            state.setCountry(country);
        }

        state.setName(stateRequestDTO.getName());
        state.setAcronym(stateRequestDTO.getAcronym());
        state = stateRepository.save(state);

        return DayOffMarkerResponse.<StateResponseDTO>builder()
                .data(stateMapper.toDTO(state))
                .build();
    }

    @Override
    public State findStateByStateId(final Long stateId) throws DayOffMarkerGenericException {
        Optional<State> optionalState = stateRepository.findById(stateId);
        return optionalState.orElseThrow(StateNotExistException::new);
    }
}
