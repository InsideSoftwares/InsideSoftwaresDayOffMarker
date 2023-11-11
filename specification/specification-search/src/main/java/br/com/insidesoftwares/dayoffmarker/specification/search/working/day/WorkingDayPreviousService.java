package br.com.insidesoftwares.dayoffmarker.specification.search.working.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.day.DayDTO;

import java.time.LocalDate;

public interface WorkingDayPreviousService {
    DayDTO findWorkingDayPrevious(final LocalDate date, final int numberOfDays);
}
