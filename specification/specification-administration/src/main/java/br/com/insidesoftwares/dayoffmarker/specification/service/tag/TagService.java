package br.com.insidesoftwares.dayoffmarker.specification.service.tag;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface TagService {

    InsideSoftwaresResponseDTO<UUID> save(final @Valid TagRequestDTO tagRequestDTO);

    void update(final UUID tagID, final @Valid TagRequestDTO tagRequestDTO);

    InsideSoftwaresResponseDTO<TagLinkResponseDTO> linkTagByDay(final @Valid TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);

    InsideSoftwaresResponseDTO<TagLinkResponseDTO> unlinkTagByDay(final @Valid TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);
}
