package br.com.insidesoftwares.dayoffmarker.specification.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;

import java.time.LocalDate;

public interface WorkingDayService {

	InsideSoftwaresResponse<DayDTO> findWorkingDay(
		final LocalDate date,
		final int numberOfDays
	);

	InsideSoftwaresResponse<DayDTO> findPreviousWorkingDay(
		final LocalDate date,
		final int numberOfDays
	);

	InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDay();

}
