package br.com.insidesoftwares.dayoffmarker.specification.search.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

public interface WorkingStateService {
    InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingStateByDay(final UUID stateID, final LocalDate date);
    InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(final UUID stateID);
}
