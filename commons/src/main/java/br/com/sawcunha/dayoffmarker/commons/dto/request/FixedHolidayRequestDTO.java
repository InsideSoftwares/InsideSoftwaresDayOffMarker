package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
@Data
public class FixedHolidayRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String name;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String description;

    @NotNull(message = "DOMV-003")
    @Min(value = 1, message = "DOMV-002")
    @Max(value = 31, message = "DOMV-004")
    private Integer day;

    @NotNull(message = "DOMV-003")
    @Min(value = 1, message = "DOMV-002")
    @Max(value = 12, message = "DOMV-004")
    private Integer month;

    @NotNull(message = "DOMV-003")
    private Long countryId;

    @NotNull(message = "DOMV-003")
    private Boolean isOptional;

    private LocalTime fromTime;
}
