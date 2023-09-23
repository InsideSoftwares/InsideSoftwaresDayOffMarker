package br.com.insidesoftwares.dayoffmarker.specification.service.working.day;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;

public interface WorkingDayService {
	InsideSoftwaresResponseDTO<DayDTO> findNextWorkingDay( final LocalDate date, final int numberOfDays );
	InsideSoftwaresResponseDTO<DayDTO> findPreviousWorkingDay( final LocalDate date, final int numberOfDays );
	InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDay();
}
