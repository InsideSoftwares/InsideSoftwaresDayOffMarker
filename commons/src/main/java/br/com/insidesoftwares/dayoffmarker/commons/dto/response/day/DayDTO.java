package br.com.insidesoftwares.dayoffmarker.commons.dto.response.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Builder
public record DayDTO(
        Long id,
        LocalDate date,
        boolean weekend,
        boolean holiday,
        DayOfWeek dayOfWeek,
        Integer dayOfYear,
        Set<HolidayResponseDTO> holidays,
        Set<TagResponseDTO> tags
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 7473865903890039251L;
}
