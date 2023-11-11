package br.com.insidesoftwares.dayoffmarker.commons.dto.tag;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder
public record TagResponseDTO(
        UUID id,
        String code,
        String description
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -7923904950266131177L;
}
