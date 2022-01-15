package br.com.sawcunha.dayoffmarker.commons.dto.request;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
@Data
public class HolidayRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
