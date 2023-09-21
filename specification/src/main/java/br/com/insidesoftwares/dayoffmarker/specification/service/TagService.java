package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TagService {

    InsideSoftwaresResponseDTO<List<TagResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter);

    InsideSoftwaresResponseDTO<TagResponseDTO> findById(final Long tagID);

    void save(final @Valid TagRequestDTO tagRequestDTO);
    void update(final Long tagID, final @Valid TagRequestDTO tagRequestDTO);

	InsideSoftwaresResponseDTO<TagLinkResponseDTO> linkTagByDay(final @Valid TagLinkRequestDTO tagLinkRequestDTO);
	InsideSoftwaresResponseDTO<TagLinkResponseDTO> unlinkTagByDay(final @Valid TagLinkRequestDTO tagLinkRequestDTO);

}
