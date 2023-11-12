package br.com.insidesoftwares.dayoffmarker.specification.search.state;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface StateSearchService {
    InsideSoftwaresResponseDTO<List<StateResponseDTO>> findAll(final String nameCountry, final InsidePaginationFilterDTO paginationFilter);
    InsideSoftwaresResponseDTO<StateResponseDTO> findById(final UUID stateID);
    State findStateByStateId(final UUID stateId);
}
