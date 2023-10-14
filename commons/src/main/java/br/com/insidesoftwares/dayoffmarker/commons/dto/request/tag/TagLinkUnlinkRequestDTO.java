package br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

@Builder
public record TagLinkUnlinkRequestDTO(
        @NotEmpty(message = "DOMV-003")
        Set<Long> tagsID,
        DayOfWeek dayOfWeek,
        @Min(value = 1, message = "DOMV-002")
        @Max(value = 366, message = "DOMV-004")
        Integer dayOfYear,
        @Min(value = 1, message = "DOMV-002")
        @Max(value = 31, message = "DOMV-004")
        Integer day,
        @Min(value = 1, message = "DOMV-002")
        @Max(value = 12, message = "DOMV-004")
        Integer month,
        @Min(value = 2023, message = "DOMV-002")
        Integer year
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -5201890574726545927L;
}
