package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface StateService {

    InsideSoftwaresResponseDTO<Long> save(final @Valid StateRequestDTO stateRequestDTO);

    void update(final Long stateID, final @Valid StateRequestDTO stateRequestDTO);

    void addStateHoliday(final Long stateID, final @Valid StateHolidayRequestDTO stateHolidayRequestDTO);

    void deleteStateHoliday(final Long stateID, final @Valid StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO);
}
