package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderFixedHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.FixedHoliday;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface FixedHolidayService {

    InsideSoftwaresResponse<List<FixedHolidayResponseDTO>> findAll(
            final PaginationFilter<eOrderFixedHoliday> paginationFilter
    );

    InsideSoftwaresResponse<FixedHolidayResponseDTO> findById(final Long fixedHolidayID) throws FixedHolidayNotExistException;

    void save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO);
    void update(final Long fixedHolidayID, final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO);

	List<FixedHoliday> findAll();
	FixedHoliday findFixedHolidayById(final Long fixedHolidayID) throws FixedHolidayNotExistException;

}
