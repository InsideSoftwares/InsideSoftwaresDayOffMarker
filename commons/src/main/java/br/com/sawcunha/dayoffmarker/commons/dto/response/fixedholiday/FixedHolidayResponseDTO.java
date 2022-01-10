package br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class FixedHolidayResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Integer day;
    private Integer month;
    private boolean isOptional;
    private LocalTime fromTime;
    private Long countryId;
    private String countryName;
}
