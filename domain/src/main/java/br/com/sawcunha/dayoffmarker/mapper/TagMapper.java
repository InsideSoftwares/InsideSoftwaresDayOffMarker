package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.commons.dto.request.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.sawcunha.dayoffmarker.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

	TagResponseDTO toDTO(Tag tag);
	Tag toEntity(TagRequestDTO tagRequestDTO);
    List<TagResponseDTO> toDTOs(List<Tag> tags);

}
