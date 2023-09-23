package br.com.insidesoftwares.dayoffmarker.specification.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;

public interface WorkingCityService {
	InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCityByDay(final Long cityID, final LocalDate date);
	InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(final Long cityID);
}
