package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface FixedHolidaySearchService {
    InsideSoftwaresResponseDTO<List<FixedHolidayResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<FixedHolidayResponseDTO> findById(final Long fixedHolidayID);

    List<FixedHoliday> findAllByEnable(final boolean enable);

    FixedHoliday findFixedHolidayById(final Long fixedHolidayID);
}
