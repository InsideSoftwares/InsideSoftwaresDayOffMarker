package br.com.insidesoftwares.dayoffmarker.specification.search.tag;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagResponseDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface TagSearchService {
    InsideSoftwaresResponseDTO<List<TagResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<TagResponseDTO> findById(final UUID tagID);
}
