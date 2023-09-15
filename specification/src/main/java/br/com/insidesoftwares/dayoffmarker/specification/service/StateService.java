package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderState;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface StateService {

    InsideSoftwaresResponseDTO<List<StateResponseDTO>> findAll(
            final String nameCountry,
			final InsidePaginationFilterDTO<eOrderState> paginationFilter
    );

    InsideSoftwaresResponseDTO<StateResponseDTO> findById(final Long stateID);

    void save(final @Valid StateRequestDTO stateRequestDTO);
    void update(final Long stateID, final @Valid StateRequestDTO stateRequestDTO);

    State findStateByStateId(final Long stateId);

	void addStateHoliday(final Long stateID, final @Valid StateHolidayRequestDTO stateHolidayRequestDTO);
	void deleteStateHoliday(final Long stateID, final @Valid StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO);

}
