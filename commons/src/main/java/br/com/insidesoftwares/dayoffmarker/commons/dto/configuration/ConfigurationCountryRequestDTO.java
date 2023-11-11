package br.com.insidesoftwares.dayoffmarker.commons.dto.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record ConfigurationCountryRequestDTO(
        @NotNull(message = "DOMV-003")
        @NotEmpty(message = "DOMV-003")
        String country
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -890203176340888973L;
}
