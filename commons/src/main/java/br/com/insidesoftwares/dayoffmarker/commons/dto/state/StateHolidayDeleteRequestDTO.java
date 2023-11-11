package br.com.insidesoftwares.dayoffmarker.commons.dto.state;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public record StateHolidayDeleteRequestDTO(
        @NotNull(message = "DOMV-003") @NotEmpty(message = "DOMV-001") Set<UUID> holidaysId
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 2042730745217013403L;
}
