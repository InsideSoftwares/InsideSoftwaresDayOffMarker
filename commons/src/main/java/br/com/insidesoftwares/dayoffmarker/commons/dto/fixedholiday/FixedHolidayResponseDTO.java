package br.com.insidesoftwares.dayoffmarker.commons.dto.fixedholiday;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record FixedHolidayResponseDTO(
        UUID id,
        String name,
        String description,
        Integer day,
        Integer month,
        boolean isOptional,
        LocalTime fromTime,
        UUID countryId,
        String countryName
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -4681536593086537632L;
}
