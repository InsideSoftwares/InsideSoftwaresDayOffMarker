package br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record HolidayRequestDTO(
        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-001")
        String name,

        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-001")
        String description,

        @NotNull(message = "DOMV-003")
        TypeHoliday holidayType,

        LocalTime fromTime,
        boolean optional,
        boolean nationalHoliday,

        @NotNull(message = "DOMV-003")
        Long dayId
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 7088171460921890362L;
}
