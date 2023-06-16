package br.com.insidesoftwares.dayoffmarker.specification.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;

public interface WorkingStateService {

	InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingStateByDay(final Long stateID, final LocalDate date);
	InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(final Long stateID);
}
