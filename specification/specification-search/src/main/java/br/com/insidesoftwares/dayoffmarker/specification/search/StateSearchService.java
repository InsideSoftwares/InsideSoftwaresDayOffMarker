package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface StateSearchService {
    InsideSoftwaresResponseDTO<List<StateResponseDTO>> findAll(final String nameCountry, final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<StateResponseDTO> findById(final Long stateID);

    State findStateByStateId(final Long stateId);
}
