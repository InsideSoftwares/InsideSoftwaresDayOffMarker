package br.com.insidesoftwares.dayoffmarker.specification.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;

public interface WorkingCityService {

	InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCityByDay(final Long cityID, final LocalDate date);

	InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(final Long cityID);
}
