package br.com.insidesoftwares.dayoffmarker.specification.service.working.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;

import java.time.LocalDate;

public interface WorkingDayNextService {
    DayDTO findWorkingDayNext( final LocalDate date, final int numberOfDays);
}
