package br.com.insidesoftwares.dayoffmarker.domain.mapper.city;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CityHolidayMapper.class})
public interface CityMapper {

    @Mappings({
            @Mapping(source = "state.id", target = "stateID"),
            @Mapping(source = "state.name", target = "stateName"),
            @Mapping(source = "state.acronym", target = "stateAcronym"),
            @Mapping(source = "cityHolidays", target = "cityHolidays", ignore = true)
    })
    CityResponseDTO toDTO(City city);

    default List<CityResponseDTO> toDTOs(List<City> cities) {
        return cities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(source = "state.id", target = "stateID"),
            @Mapping(source = "state.name", target = "stateName"),
            @Mapping(source = "state.acronym", target = "stateAcronym"),
            @Mapping(source = "cityHolidays", target = "cityHolidays", qualifiedByName = "toCityHolidaysDTO")
    })
    CityResponseDTO toFullDTO(City city);
}
