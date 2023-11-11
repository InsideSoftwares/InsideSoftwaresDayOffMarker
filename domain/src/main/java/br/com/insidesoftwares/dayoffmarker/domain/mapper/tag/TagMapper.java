package br.com.insidesoftwares.dayoffmarker.domain.mapper.tag;

import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponseDTO toDTO(Tag tag);

    Tag toEntity(TagRequestDTO tagRequestDTO);

    List<TagResponseDTO> toDTOs(List<Tag> tags);

    @Named("toTagResponseDTO")
    Set<TagResponseDTO> toDTOs(Set<Tag> tags);

}
