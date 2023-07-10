package br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record HolidayResponseDTO(
	Long id,
	String name,
	String description,
	TypeHoliday holidayType,
	LocalTime fromTime,
	LocalDate day
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -3943327374923194857L;
}
