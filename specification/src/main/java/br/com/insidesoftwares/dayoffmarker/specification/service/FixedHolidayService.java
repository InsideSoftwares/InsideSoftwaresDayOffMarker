package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface FixedHolidayService {
    InsideSoftwaresResponseDTO<List<FixedHolidayResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<FixedHolidayResponseDTO> findById(final Long fixedHolidayID);

    InsideSoftwaresResponseDTO<Long> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO);

    void update(final Long fixedHolidayID, final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO);

    List<FixedHoliday> findAllByEnable(final boolean enable);

    FixedHoliday findFixedHolidayById(final Long fixedHolidayID) throws FixedHolidayNotExistException;
}
