package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
@Data
public class FixedHolidayUpdateRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String name;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String description;

    @NotNull(message = "DOMV-003")
    private Boolean isOptional;

    private LocalTime fromTime;
}
