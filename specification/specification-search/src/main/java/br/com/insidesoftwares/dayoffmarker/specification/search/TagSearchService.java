package br.com.insidesoftwares.dayoffmarker.specification.search;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TagSearchService {
    InsideSoftwaresResponseDTO<List<TagResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<TagResponseDTO> findById(final Long tagID);
}
