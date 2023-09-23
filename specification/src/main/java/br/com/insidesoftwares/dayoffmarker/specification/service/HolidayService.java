package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
public interface HolidayService {
    InsideSoftwaresResponseDTO<List<HolidayResponseDTO>> findAll( final LocalDate startDate, final LocalDate endDate, final InsidePaginationFilterDTO paginationFilter);
    InsideSoftwaresResponseDTO<HolidayResponseDTO> findById(final Long holidayID);
    InsideSoftwaresResponseDTO<Long> save(final @Valid HolidayRequestDTO holidayRequestDTO);
    InsideSoftwaresResponseDTO<List<Long>> saveInBatch(final @Valid HolidayBatchRequestDTO holidayBatchRequestDTO);
    void update(final Long holidayID, final @Valid HolidayRequestDTO holidayRequestDTO);
	void saveHoliday(final @Valid HolidayCreateRequestDTO holidayCreateRequestDTO);
    Holiday findHolidayById(final Long holidayID);
}
