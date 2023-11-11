package br.com.insidesoftwares.dayoffmarker.commons.dto.city;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
public record CityResponseDTO(
        UUID id,
        String name,
        String acronym,
        String code,
        UUID stateID,
        String stateName,
        String stateAcronym,
        List<CityHolidayResponseDTO> cityHolidays
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 2579228077242679677L;
}
