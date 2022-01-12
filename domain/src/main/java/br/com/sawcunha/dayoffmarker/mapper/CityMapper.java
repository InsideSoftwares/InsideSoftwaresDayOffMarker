package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.sawcunha.dayoffmarker.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mappings({
            @Mapping(source = "state.id", target = "stateID"),
            @Mapping(source = "state.name", target = "stateName"),
            @Mapping(source = "state.acronym", target = "stateAcronym")
    })
    CityResponseDTO toDTO(City city);
    List<CityResponseDTO> toDTOs(List<City> cities);

}
