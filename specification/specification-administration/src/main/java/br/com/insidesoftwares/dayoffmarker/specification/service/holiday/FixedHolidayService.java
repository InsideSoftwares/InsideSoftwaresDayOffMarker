package br.com.insidesoftwares.dayoffmarker.specification.service.holiday;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayUpdateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface FixedHolidayService {

    InsideSoftwaresResponseDTO<UUID> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO);

    void update(final UUID fixedHolidayID, final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO);

}
