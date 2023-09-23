package br.com.insidesoftwares.dayoffmarker.commons.dto.response.state;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record StateHolidayResponseDTO(
        Long id,
        String name,
        String description,
        TypeHoliday holidayType,
        LocalTime fromTime,
        LocalDate day,
        boolean stateHoliday
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -3060795851645370393L;
}
