package br.com.insidesoftwares.dayoffmarker.commons.dto.response.country;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record CountryResponseDTO(
	Long id,
	String name,
	String acronym,
	String code
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1526266160816424514L;
}
