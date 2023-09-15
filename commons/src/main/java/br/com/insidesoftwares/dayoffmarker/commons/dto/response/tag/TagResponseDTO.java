package br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record TagResponseDTO(
	Long id,
	String code,
	String description
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -7923904950266131177L;
}
