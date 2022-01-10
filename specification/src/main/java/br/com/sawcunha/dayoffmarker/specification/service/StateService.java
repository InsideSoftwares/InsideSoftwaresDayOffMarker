package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.StateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderState;
import br.com.sawcunha.dayoffmarker.entity.State;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface StateService {

    DayOffMarkerResponse<List<StateResponseDTO>> findAll(
            final String nameCountry,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderState orderState
    ) throws Exception;

    DayOffMarkerResponse<StateResponseDTO> findById(Long stateID) throws Exception;

    DayOffMarkerResponse<StateResponseDTO> save(final @Valid StateRequestDTO stateRequestDTO) throws Exception;
    DayOffMarkerResponse<StateResponseDTO> update(final Long stateID, final @Valid StateRequestDTO stateRequestDTO) throws Exception;

    State findStateByStateId(final Long stateId) throws Exception;

}
