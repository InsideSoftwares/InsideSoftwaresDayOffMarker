package br.com.insidesoftwares.dayoffmarker.specification.search.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

public interface WorkingCityService {
    InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCityByDay(final UUID cityID, final LocalDate date);
    InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(final UUID cityID);
}
