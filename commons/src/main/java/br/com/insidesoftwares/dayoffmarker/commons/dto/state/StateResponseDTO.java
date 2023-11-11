package br.com.insidesoftwares.dayoffmarker.commons.dto.state;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
public record StateResponseDTO(
        UUID id,
        String name,
        String acronym,
        String code,
        UUID countryId,
        String countryName,
        List<StateHolidayResponseDTO> stateHolidays
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 6726674871429137110L;
}
