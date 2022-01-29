package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkDayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderTag;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TagService {

    DayOffMarkerResponse<List<TagResponseDTO>> findAll(
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderTag orderTag
    ) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<TagResponseDTO> findById(final Long tagID) throws DayOffMarkerGenericException;

    DayOffMarkerResponse<TagResponseDTO> save(final @Valid TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException;
    DayOffMarkerResponse<TagResponseDTO> update(final Long tagID, final @Valid TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException;

	void linkDay(final Long tagID, final @Valid LinkDayRequestDTO linkDayRequestDTO, final Long countryID) throws DayOffMarkerGenericException;
}
