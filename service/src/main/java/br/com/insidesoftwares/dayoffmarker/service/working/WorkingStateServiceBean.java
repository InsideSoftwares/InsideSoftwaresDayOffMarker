package br.com.insidesoftwares.dayoffmarker.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.StateService;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class WorkingStateServiceBean implements WorkingStateService {

	private final StateService stateService;
	private final DayService dayService;
	private final StateRepository stateRepository;

	@Override
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingStateByDay(
		final Long stateID,
		final LocalDate date
	) {
		boolean isWorkingDay = isWorkingStateByDay(stateID, date);

		return InsideSoftwaresResponseUtils.wrapResponse(
			WorkingCurrentDayResponseDTO.builder()
				.isWorkingDay(isWorkingDay)
				.build()
		);
	}

	@Override
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(final Long stateID) {

		LocalDate currentDay = LocalDate.now();
		boolean isWorkingDay = isWorkingStateByDay(stateID, currentDay);

		return InsideSoftwaresResponseUtils.wrapResponse(
			WorkingCurrentDayResponseDTO.builder()
				.isWorkingDay(isWorkingDay)
				.build()
		);
	}

	private boolean isWorkingStateByDay(final Long stateID, final LocalDate date){
		State state = stateService.findStateByStateId(stateID);
		boolean isWorkingDay;
		boolean isNotWorkingDay = stateRepository.isStateHolidayByStateAndStateHolidayAndDate(
			state,
			true,
			date
		);

		if(isNotWorkingDay) {
			isWorkingDay = false;
		} else {
			isWorkingDay = stateRepository.isStateHolidayByStateAndStateHolidayAndDate(
				state,
				false,
				date
			);
			if(!isWorkingDay && state.getStateHolidays().isEmpty()) {
				isWorkingDay = dayService.isDayByDateAndIsWeekend(date, false);
			}
		}

		return isWorkingDay;
	}
}
