package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.CityRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderCity;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface CityService {

    DayOffMarkerResponse<List<CityResponseDTO>> findAll(
            final Long stateID,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderCity orderCity
    ) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<CityResponseDTO> findById(final Long cityID) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<CityResponseDTO> save(final @Valid CityRequestDTO cityRequestDTO) throws DayOffMarkerGenericException;
    DayOffMarkerResponse<CityResponseDTO> update(final Long cityID, final @Valid CityRequestDTO cityRequestDTO) throws DayOffMarkerGenericException;
}
