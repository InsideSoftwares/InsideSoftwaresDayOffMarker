package br.com.insidesoftwares.dayoffmarker.domain.mapper.state;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.StateHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StateHolidayMapper {

    @Mappings({
            @Mapping(source = "holiday.id", target = "id"),
            @Mapping(source = "holiday.name", target = "name"),
            @Mapping(source = "holiday.description", target = "description"),
            @Mapping(source = "holiday.holidayType", target = "holidayType"),
            @Mapping(source = "holiday.fromTime", target = "fromTime"),
            @Mapping(source = "holiday.day.date", target = "day")
    })
    StateHolidayResponseDTO toDTO(StateHoliday stateHoliday);

    @Named("toStateHolidaysDTO")
    default List<StateHolidayResponseDTO> toDTOs(Set<StateHoliday> stateHolidays) {
        return stateHolidays.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
