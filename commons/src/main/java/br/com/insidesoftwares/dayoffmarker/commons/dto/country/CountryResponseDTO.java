package br.com.insidesoftwares.dayoffmarker.commons.dto.country;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder
public record CountryResponseDTO(
        UUID id,
        String name,
        String acronym,
        String code
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1526266160816424514L;
}
