package br.com.insidesoftwares.dayoffmarker.domain.mapper.city;

import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CityHolidayMapper {

    @Mappings({
            @Mapping(source = "holiday.id", target = "id"),
            @Mapping(source = "holiday.name", target = "name"),
            @Mapping(source = "holiday.description", target = "description"),
            @Mapping(source = "holiday.holidayType", target = "holidayType"),
            @Mapping(source = "holiday.fromTime", target = "fromTime"),
            @Mapping(source = "holiday.day.date", target = "day")
    })
    CityHolidayResponseDTO toDTO(CityHoliday stateHoliday);

    @Named("toCityHolidaysDTO")
    default List<CityHolidayResponseDTO> toDTOs(Set<CityHoliday> cityHolidays) {
        return cityHolidays.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
