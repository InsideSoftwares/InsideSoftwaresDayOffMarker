package br.com.insidesoftwares.dayoffmarker.commons.dto.response.working;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record WorkingCurrentDayResponseDTO(
	boolean isWorkingDay
) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
}
