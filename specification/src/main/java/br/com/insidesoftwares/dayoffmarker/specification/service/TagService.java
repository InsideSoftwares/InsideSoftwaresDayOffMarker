package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderTag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TagService {

    InsideSoftwaresResponse<List<TagResponseDTO>> findAll(final PaginationFilter<eOrderTag> paginationFilter);

    InsideSoftwaresResponse<TagResponseDTO> findById(final Long tagID);

    void save(final @Valid TagRequestDTO tagRequestDTO);
    void update(final Long tagID, final @Valid TagRequestDTO tagRequestDTO);

	void linkTagByDay(final @Valid TagLinkRequestDTO tagLinkRequestDTO);

}
