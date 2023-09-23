package br.com.insidesoftwares.dayoffmarker.service.working;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.StateService;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WorkingStateServiceBean implements WorkingStateService {

    private final StateService stateService;
    private final DayService dayService;
    private final StateRepository stateRepository;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingStateByDay(
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

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(final Long stateID) {

        LocalDate currentDay = LocalDate.now();
        boolean isWorkingDay = isWorkingStateByDay(stateID, currentDay);

        return InsideSoftwaresResponseUtils.wrapResponse(
                WorkingCurrentDayResponseDTO.builder()
                        .isWorkingDay(isWorkingDay)
                        .build()
        );
    }

    private boolean isWorkingStateByDay(final Long stateID, final LocalDate date) {
        State state = stateService.findStateByStateId(stateID);
        boolean isWorkingDay;
        boolean isNotWorkingDay = stateRepository.isStateHolidayByStateAndStateHolidayAndDate(
                state,
                true,
                date
        );

        if (isNotWorkingDay) {
            isWorkingDay = false;
        } else {
            isWorkingDay = stateRepository.isStateHolidayByStateAndStateHolidayAndDate(
                    state,
                    false,
                    date
            );
            if (!isWorkingDay && state.getStateHolidays().isEmpty()) {
                isWorkingDay = dayService.isDayByDateAndIsWeekend(date, false);
            }
        }

        return isWorkingDay;
    }
}
