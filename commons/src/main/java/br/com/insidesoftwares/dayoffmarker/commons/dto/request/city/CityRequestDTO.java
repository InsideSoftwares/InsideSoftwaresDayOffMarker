package br.com.insidesoftwares.dayoffmarker.commons.dto.request.city;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record CityRequestDTO (
	@NotNull(message = "DOMV-003") @NotEmpty(message = "DOMV-001") String name,
	@NotNull(message = "DOMV-003") @NotEmpty(message = "DOMV-001")  @Size(min = 2, max = 10, message = "DOMV-006") String acronym,
	@NotNull(message = "DOMV-003")	@NotEmpty(message = "DOMV-001")	@Size(min = 3, max = 15, message = "DOMV-006") String code,
	@NotNull(message = "DOMV-003") Long stateID

) implements Serializable {
    @Serial
    private static final long serialVersionUID = -3155842808516668192L;
}
