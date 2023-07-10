package br.com.insidesoftwares.dayoffmarker.commons.dto.request.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record ConfigurationLimitYearRequestDTO(
	@NotNull(message = "DOMV-003")
	@Min(value = 1, message = "DOMV-002")
	@Max(value = 15, message = "DOMV-004")
	Integer numberBackYears,
	@NotNull(message = "DOMV-003")
	@Min(value = 1, message = "DOMV-002")
	@Max(value = 25, message = "DOMV-004")
	Integer numberForwardYears
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 5891167388638166940L;
}
