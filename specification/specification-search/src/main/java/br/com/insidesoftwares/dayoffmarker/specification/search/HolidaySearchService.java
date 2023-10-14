package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
public interface HolidaySearchService {
    InsideSoftwaresResponseDTO<List<HolidayResponseDTO>> findAll(final LocalDate startDate, final LocalDate endDate, final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<HolidayResponseDTO> findById(final Long holidayID);

    Holiday findHolidayById(final Long holidayID);
}
