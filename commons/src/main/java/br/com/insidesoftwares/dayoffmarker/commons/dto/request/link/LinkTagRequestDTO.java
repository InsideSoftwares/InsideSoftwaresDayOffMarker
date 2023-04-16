package br.com.insidesoftwares.dayoffmarker.commons.dto.request.link;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Builder
public record LinkTagRequestDTO(
	@NotEmpty(message = "DOMV-003")
	Set<Long> tagsID
) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
}
