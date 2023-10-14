package br.com.insidesoftwares.dayoffmarker.service.working.day;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.day.WorkingDayNextService;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.day.WorkingDayPreviousService;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.day.WorkingDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class WorkingDayServiceBean implements WorkingDayService {

    private final DayRepository dayRepository;
    private final WorkingDayNextService workingDayNextService;
    private final WorkingDayPreviousService workingDayPreviousService;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<DayDTO> findNextWorkingDay(
            final LocalDate date,
            final int numberOfDays
    ) {
        DayDTO dayDTO = workingDayNextService.findWorkingDayNext(date, numberOfDays);

        return InsideSoftwaresResponseUtils.wrapResponse(dayDTO);
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<DayDTO> findPreviousWorkingDay(
            final LocalDate date,
            final int numberOfDays
    ) {
        DayDTO dayDTO = workingDayPreviousService.findWorkingDayPrevious(date, numberOfDays);

        return InsideSoftwaresResponseUtils.wrapResponse(dayDTO);
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDay() {
        LocalDate currentDay = LocalDate.now();

        boolean isWorkingDay = dayRepository.isWorkingDayByDateAndIsHolidayAndIsWeekend(currentDay, false, false);

        return InsideSoftwaresResponseUtils.wrapResponse(
                WorkingCurrentDayResponseDTO.builder()
                        .isWorkingDay(isWorkingDay)
                        .build()
        );
    }

}
