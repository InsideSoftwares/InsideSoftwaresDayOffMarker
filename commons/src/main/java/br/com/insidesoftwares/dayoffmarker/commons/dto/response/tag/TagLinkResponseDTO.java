package br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record TagLinkResponseDTO(
        String requestID
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -6653112384210969514L;
}
