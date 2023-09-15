package br.com.insidesoftwares.dayoffmarker.commons.dto.response.city;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public record CityHolidayResponseDTO(
	Long id,
	String name,
	String description,
	TypeHoliday holidayType,
	LocalTime fromTime,
	LocalDate day,
	boolean cityHoliday
) implements Serializable {
	@Serial
	private static final long serialVersionUID = -1915541372989697861L;
}
