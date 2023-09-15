package br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;

@Builder
public record HolidayBatchRequestDTO (

	@NotNull(message = "DOMV-003")	@NotEmpty(message = "DOMV-001") String name,

	@NotNull(message = "DOMV-003") @NotEmpty(message = "DOMV-001")	String description,

	@NotNull(message = "DOMV-003") TypeHoliday holidayType,

	LocalTime fromTime,
	boolean optional,

	@NotNull(message = "DOMV-003") @Size(min = 1, max = 31, message = "DOMV-008") Set<Long> daysId

) implements Serializable {

    @Serial
    private static final long serialVersionUID = 5545796669909211361L;

}
