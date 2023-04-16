package br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration;

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
    private static final long serialVersionUID = 1L;
}
