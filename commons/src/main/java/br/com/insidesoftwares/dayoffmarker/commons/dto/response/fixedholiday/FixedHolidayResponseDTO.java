package br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record FixedHolidayResponseDTO(
        Long id,
        String name,
        String description,
        Integer day,
        Integer month,
        boolean isOptional,
        LocalTime fromTime,
        Long countryId,
        String countryName
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -4681536593086537632L;
}
