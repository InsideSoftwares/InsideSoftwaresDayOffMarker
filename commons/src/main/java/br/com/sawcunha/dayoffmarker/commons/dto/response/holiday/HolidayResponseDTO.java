package br.com.sawcunha.dayoffmarker.commons.dto.response.holiday;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class HolidayResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private eTypeHoliday holidayType;
    private LocalTime fromTime;
    private LocalDate day;
    private String countryName;

}
