package br.com.insidesoftwares.dayoffmarker.commons.dto.request.state;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public record StateHolidayDeleteRequestDTO(
	@NotNull(message = "DOMV-003") @NotEmpty(message = "DOMV-001") Set<Long> holidaysId
) implements Serializable {
	@Serial
	private static final long serialVersionUID = 2042730745217013403L;
}
