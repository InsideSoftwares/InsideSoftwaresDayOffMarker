package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DayService {
    void linkTag(final Long dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO);

    void unlinkTag(final Long dayID, final @Valid LinkTagRequestDTO linkTagRequestDTO);

    void defineDayIsHoliday(final Long dayID);
}
