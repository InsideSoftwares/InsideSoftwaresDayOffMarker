package br.com.insidesoftwares.dayoffmarker.commons.dto.tag;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Builder
public record LinkTagRequestDTO(
        @NotEmpty(message = "DOMV-003")
        Set<UUID> tagsID
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 6539008299760592614L;
}
