package br.com.insidesoftwares.dayoffmarker.service.working;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.day.DaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.state.StateSearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.WorkingStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class WorkingStateServiceBean implements WorkingStateService {

    private final StateSearchService stateSearchService;
    private final DaySearchService daySearchService;
    private final StateRepository stateRepository;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingStateByDay(
            final UUID stateID,
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
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(final UUID stateID) {

        LocalDate currentDay = LocalDate.now();
        boolean isWorkingDay = isWorkingStateByDay(stateID, currentDay);

        return InsideSoftwaresResponseUtils.wrapResponse(
                WorkingCurrentDayResponseDTO.builder()
                        .isWorkingDay(isWorkingDay)
                        .build()
        );
    }

    private boolean isWorkingStateByDay(final UUID stateID, final LocalDate date) {
        State state = stateSearchService.findStateByStateId(stateID);
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
                isWorkingDay = daySearchService.isDayByDateAndIsWeekend(date, false);
            }
        }

        return isWorkingDay;
    }
}
