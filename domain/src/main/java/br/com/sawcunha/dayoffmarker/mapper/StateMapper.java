package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.sawcunha.dayoffmarker.entity.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StateMapper {

    @Mappings({
            @Mapping(source = "country.id", target = "countryId"),
            @Mapping(source = "country.name", target = "countryName")
    })
    StateResponseDTO toDTO(State state);
    List<StateResponseDTO> toDTOs(List<State> states);

}
