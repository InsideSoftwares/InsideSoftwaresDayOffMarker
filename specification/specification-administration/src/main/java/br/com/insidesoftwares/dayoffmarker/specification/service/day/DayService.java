package br.com.insidesoftwares.dayoffmarker.specification.service.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.LinkTagRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface DayService {
    void linkTag(final UUID dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO);
    void unlinkTag(final UUID dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO);
    void defineDayIsHoliday(final UUID dayID);
}
