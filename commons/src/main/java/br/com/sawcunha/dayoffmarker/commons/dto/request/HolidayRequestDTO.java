package br.com.sawcunha.dayoffmarker.commons.dto.request;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Builder
@Data
public class HolidayRequestDTO {

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String name;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String description;

    @NotNull(message = "DOMV-003")
    private eTypeHoliday holidayType;
    private LocalTime fromTime;

    @NotNull(message = "DOMV-003")
    private Long dayId;

}
