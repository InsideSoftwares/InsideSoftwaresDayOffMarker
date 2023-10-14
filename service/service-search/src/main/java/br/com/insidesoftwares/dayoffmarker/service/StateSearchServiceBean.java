package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderState;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.state.StateMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.CountrySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.StateSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class StateSearchServiceBean implements StateSearchService {

    private final StateRepository stateRepository;
    private final CountrySearchService countrySearchService;
    private final StateMapper stateMapper;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<StateResponseDTO>> findAll(
            final String nameCountry,
            final InsidePaginationFilterDTO paginationFilter
    ) {

        Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderState.ID);

        Country country = countrySearchService.findCountryByNameOrDefault(nameCountry);

        Page<State> states = stateRepository.findAllByCountry(country, pageable);

        return InsideSoftwaresResponseDTO.<List<StateResponseDTO>>builder()
                .data(stateMapper.toDTOs(states.getContent()))
                .insidePaginatedDTO(
                        PaginationUtils.createPaginated(
                                states.getTotalPages(),
                                states.getTotalElements(),
                                states.getContent().size(),
                                paginationFilter.getSizePerPage()
                        )
                )
                .build();
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<StateResponseDTO> findById(final Long stateID) {
        State state = stateRepository.findStateById(stateID).orElseThrow(StateNotExistException::new);

        return InsideSoftwaresResponseDTO.<StateResponseDTO>builder()
                .data(stateMapper.toFullDTO(state))
                .build();
    }

    @InsideAudit
    @Override
    public State findStateByStateId(final Long stateId) {
        return stateRepository.findById(stateId).orElseThrow(StateNotExistException::new);
    }
}
