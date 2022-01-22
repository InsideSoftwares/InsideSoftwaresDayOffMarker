package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayUpdateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderFixedHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface FixedHolidayService {

    DayOffMarkerResponse<List<FixedHolidayResponseDTO>> findAll(
            final String nameCountry,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderFixedHoliday orderFixedHoliday
    ) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<FixedHolidayResponseDTO> findById(final Long fixedHolidayID) throws FixedHolidayNotExistException;

    DayOffMarkerResponse<FixedHolidayResponseDTO> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException;
    DayOffMarkerResponse<FixedHolidayResponseDTO> update(final Long fixedHolidayID, final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException;

	List<FixedHoliday> findAllByCountry() throws DayOffMarkerGenericException;
	FixedHoliday findFixedHolidayById(final Long fixedHolidayID) throws FixedHolidayNotExistException;

}
