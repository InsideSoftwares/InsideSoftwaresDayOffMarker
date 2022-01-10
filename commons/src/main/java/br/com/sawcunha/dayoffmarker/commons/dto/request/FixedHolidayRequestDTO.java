package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Builder
@Data
public class FixedHolidayRequestDTO {

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    private String name;

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    private String description;

    @NotNull(message = "DCYV-003")
    @Min(value = 1, message = "DCYV-002")
    @Max(value = 31, message = "DCYV-004")
    private Integer day;

    @NotNull(message = "DCYV-003")
    @Min(value = 1, message = "DCYV-002")
    @Max(value = 12, message = "DCYV-004")
    private Integer month;

    @NotNull(message = "DCYV-003")
    private Long countryId;

    @NotNull(message = "DCYV-003")
    private Boolean isOptional;

    private LocalTime fromTime;
}
