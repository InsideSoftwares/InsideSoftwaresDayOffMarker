package br.com.insidesoftwares.dayoffmarker.specification.service.state;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface StateService {

    InsideSoftwaresResponseDTO<UUID> save(final @Valid StateRequestDTO stateRequestDTO);
    void update(final UUID stateID, final @Valid StateRequestDTO stateRequestDTO);
    void addStateHoliday(final UUID stateID, final @Valid StateHolidayRequestDTO stateHolidayRequestDTO);
    void deleteStateHoliday(final UUID stateID, final @Valid StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO);
}
