package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.StateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderState;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
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
    ) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<StateResponseDTO> findById(Long stateID) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<StateResponseDTO> save(final @Valid StateRequestDTO stateRequestDTO) throws DayOffMarkerGenericException;
    DayOffMarkerResponse<StateResponseDTO> update(final Long stateID, final @Valid StateRequestDTO stateRequestDTO) throws DayOffMarkerGenericException;

    State findStateByStateId(final Long stateId) throws DayOffMarkerGenericException;

}
