package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderHoliday;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface HolidayService {

    DayOffMarkerResponse<List<HolidayDTO>> findAll(
            final String nameCountry,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderHoliday orderHoliday
    ) throws Exception;

    DayOffMarkerResponse<HolidayDTO> findById(Long holidayID) throws Exception;

}
