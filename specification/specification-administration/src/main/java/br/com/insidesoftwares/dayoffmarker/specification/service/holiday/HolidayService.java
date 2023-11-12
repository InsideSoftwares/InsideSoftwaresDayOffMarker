package br.com.insidesoftwares.dayoffmarker.specification.service.holiday;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface HolidayService {

    InsideSoftwaresResponseDTO<UUID> save(final @Valid HolidayRequestDTO holidayRequestDTO);
    InsideSoftwaresResponseDTO<List<UUID>> saveInBatch(final @Valid HolidayBatchRequestDTO holidayBatchRequestDTO);
    void update(final UUID holidayID, final @Valid HolidayRequestDTO holidayRequestDTO);
}
