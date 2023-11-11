package br.com.insidesoftwares.dayoffmarker.commons.dto.tag;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record TagRequestDTO(
        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-001")
        String code,
        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-001")
        String description
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 5419965774511563760L;
}
