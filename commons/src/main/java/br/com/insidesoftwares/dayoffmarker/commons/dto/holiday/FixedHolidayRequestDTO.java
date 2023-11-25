package br.com.insidesoftwares.dayoffmarker.commons.dto.holiday;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record FixedHolidayRequestDTO(
        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-001")
        String name,
        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-001")
        String description,
        @NotNull(message = "DOMV-003")
        @Min(value = 1, message = "DOMV-002")
        @Max(value = 31, message = "DOMV-004")
        int day,
        @NotNull(message = "DOMV-003")
        @Min(value = 1, message = "DOMV-002")
        @Max(value = 12, message = "DOMV-004")
        int month,
        @NotNull(message = "DOMV-003")
        boolean optional,
        LocalTime fromTime,
        boolean enable
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -2741754088344751346L;
}