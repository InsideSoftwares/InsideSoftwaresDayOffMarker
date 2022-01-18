package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderTag;
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
    ) throws Exception;

    DayOffMarkerResponse<TagResponseDTO> findById(final Long tagID) throws Exception;

    DayOffMarkerResponse<TagResponseDTO> save(final @Valid TagRequestDTO tagRequestDTO) throws Exception;
    DayOffMarkerResponse<TagResponseDTO> update(final Long tagID, final @Valid TagRequestDTO tagRequestDTO) throws Exception;
}
