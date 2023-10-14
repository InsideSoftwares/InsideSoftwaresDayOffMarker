package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface FixedHolidayService {

    InsideSoftwaresResponseDTO<Long> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO);

    void update(final Long fixedHolidayID, final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO);

}
