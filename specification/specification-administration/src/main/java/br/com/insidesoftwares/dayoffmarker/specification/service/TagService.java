package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagLinkResponseDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TagService {

    InsideSoftwaresResponseDTO<Long> save(final @Valid TagRequestDTO tagRequestDTO);

    void update(final Long tagID, final @Valid TagRequestDTO tagRequestDTO);

    InsideSoftwaresResponseDTO<TagLinkResponseDTO> linkTagByDay(final @Valid TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);

    InsideSoftwaresResponseDTO<TagLinkResponseDTO> unlinkTagByDay(final @Valid TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);
}
