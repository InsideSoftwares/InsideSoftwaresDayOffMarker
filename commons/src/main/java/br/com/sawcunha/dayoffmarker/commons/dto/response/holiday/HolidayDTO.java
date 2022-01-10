package br.com.sawcunha.dayoffmarker.commons.dto.response.holiday;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class HolidayDTO {

    private Long id;
    private String name;
    private String description;
    private eTypeHoliday holidayType;
    private LocalTime fromTime;
    private LocalDate day;

}
