package br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record FixedHolidayUpdateRequestDTO(
	@NotNull(message = "DOMV-003")
	@NotEmpty(message = "DOMV-001")
	String name,
	@NotNull(message = "DOMV-003")
	@NotEmpty(message = "DOMV-001")
	String description,
	@NotNull(message = "DOMV-003")
	Boolean isOptional,
	LocalTime fromTime,
	Boolean isEnable
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -5787629811639151444L;
}
