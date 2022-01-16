package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderHoliday;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Validated
public interface HolidayService {

	DayOffMarkerResponse<List<HolidayResponseDTO>> findAll(
			final LocalDate startDate,
			final LocalDate endDate,
			final String nameCountry,
			final int page,
			final int sizePerPage,
			final Sort.Direction direction,
			final eOrderHoliday orderHoliday
	) throws Exception;

    DayOffMarkerResponse<HolidayResponseDTO> findById(final Long holidayID) throws Exception;

    DayOffMarkerResponse<HolidayResponseDTO> save(final @Valid HolidayRequestDTO holidayRequestDTO) throws Exception;
    DayOffMarkerResponse<HolidayResponseDTO> update(final Long holidayID, final @Valid HolidayRequestDTO holidayRequestDTO) throws Exception;

	void saveHoliday(final @Valid HolidayRequestDTO holidayRequestDTO) throws Exception;


}
