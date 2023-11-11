package br.com.insidesoftwares.dayoffmarker.specification.search.holiday;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Validated
public interface HolidaySearchService {
    InsideSoftwaresResponseDTO<List<HolidayResponseDTO>> findAll(final LocalDate startDate, final LocalDate endDate, final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<HolidayResponseDTO> findById(final UUID holidayID);

    Holiday findHolidayById(final UUID holidayID);
}
