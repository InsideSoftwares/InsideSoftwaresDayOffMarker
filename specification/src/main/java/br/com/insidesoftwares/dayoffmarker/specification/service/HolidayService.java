package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.Holiday;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
public interface HolidayService {

	InsideSoftwaresResponse<List<HolidayResponseDTO>> findAll(
			final LocalDate startDate,
			final LocalDate endDate,
			final PaginationFilter<eOrderHoliday> paginationFilter
	);

    InsideSoftwaresResponse<HolidayResponseDTO> findById(final Long holidayID);

    void save(final @Valid HolidayRequestDTO holidayRequestDTO);
	void saveInBatch(final @Valid HolidayBatchRequestDTO holidayBatchRequestDTO);
    void update(final Long holidayID, final @Valid HolidayRequestDTO holidayRequestDTO);

	void saveHoliday(final @Valid HolidayRequestDTO holidayRequestDTO);

	Holiday findHolidayById(final Long holidayID);

}
