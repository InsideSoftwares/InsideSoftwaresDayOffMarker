package br.com.insidesoftwares.dayoffmarker.commons.dto.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record HolidayCreateRequestDTO(
        String name,
        String description,
        TypeHoliday holidayType,
        LocalTime fromTime,
        boolean optional,
        boolean nationalHoliday,
        UUID dayId,
        UUID fixedHolidayID
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 7948167387544767535L;
}
