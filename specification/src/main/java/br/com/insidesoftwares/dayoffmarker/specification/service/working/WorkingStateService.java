package br.com.insidesoftwares.dayoffmarker.specification.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;

public interface WorkingStateService {
	InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingStateByDay(final Long stateID, final LocalDate date);
	InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(final Long stateID);
}
