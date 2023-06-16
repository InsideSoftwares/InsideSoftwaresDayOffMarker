package br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class TagRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String code;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String description;
}
