package br.com.insidesoftwares.dayoffmarker.commons.dto.batch;

import lombok.Builder;

import java.util.UUID;

@Builder
public record LinkTagDTO(
        UUID tagID,
        UUID dayID
) {
}
