package br.com.insidesoftwares.dayoffmarker.commons.dto.response.city;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
public record CityResponseDTO (
	Long id,
	String name,
	String acronym,
	String code,
	Long stateID,
	String stateName,
	String stateAcronym,
	List<CityHolidayResponseDTO> cityHolidays
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 2579228077242679677L;
}
