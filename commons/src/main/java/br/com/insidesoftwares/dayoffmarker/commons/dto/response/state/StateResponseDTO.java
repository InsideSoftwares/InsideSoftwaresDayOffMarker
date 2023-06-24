package br.com.insidesoftwares.dayoffmarker.commons.dto.response.state;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
public record StateResponseDTO(
	Long id,
	String name,
	String acronym,
	Long countryId,
	String countryName,
	List<StateHolidayResponseDTO> stateHolidays
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}