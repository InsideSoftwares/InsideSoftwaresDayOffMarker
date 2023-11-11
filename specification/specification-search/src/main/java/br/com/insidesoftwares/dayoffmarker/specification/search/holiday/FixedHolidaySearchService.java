package br.com.insidesoftwares.dayoffmarker.specification.search.holiday;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface FixedHolidaySearchService {
    InsideSoftwaresResponseDTO<List<FixedHolidayResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<FixedHolidayResponseDTO> findById(final UUID fixedHolidayID);

    List<FixedHoliday> findAllByEnable(final boolean enable);

    FixedHoliday findFixedHolidayById(final UUID fixedHolidayID);
}
