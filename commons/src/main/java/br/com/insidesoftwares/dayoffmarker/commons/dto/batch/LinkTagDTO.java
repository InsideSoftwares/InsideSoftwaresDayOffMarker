package br.com.insidesoftwares.dayoffmarker.commons.dto.batch;

import lombok.Builder;

@Builder
public record LinkTagDTO(
        Long tagID,
        Long dayID
) {
}
