package br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Builder
@Data
public class FixedHolidayResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
