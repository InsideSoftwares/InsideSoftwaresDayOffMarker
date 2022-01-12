package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.entity.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    @Mappings({
            @Mapping(source = "day.date", target = "day"),
            @Mapping(source = "day.country.name", target = "countryName")
    })
    HolidayResponseDTO toDTO(Holiday holiday);
    List<HolidayResponseDTO> toDTOs(List<Holiday> holidays);

}
