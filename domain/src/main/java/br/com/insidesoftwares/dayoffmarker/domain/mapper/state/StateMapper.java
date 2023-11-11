package br.com.insidesoftwares.dayoffmarker.domain.mapper.state;

import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {StateHolidayMapper.class})
public interface StateMapper {

    @Mappings({
            @Mapping(source = "country.id", target = "countryId"),
            @Mapping(source = "country.name", target = "countryName"),
            @Mapping(target = "stateHolidays", source = "stateHolidays", ignore = true)
    })
    StateResponseDTO toDTO(State state);

    default List<StateResponseDTO> toDTOs(List<State> states) {
        return states.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(source = "country.id", target = "countryId"),
            @Mapping(source = "country.name", target = "countryName"),
            @Mapping(source = "stateHolidays", target = "stateHolidays", qualifiedByName = "toStateHolidaysDTO")
    })
    StateResponseDTO toFullDTO(State state);

}
