package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FixedHolidayMapper {

    @Mappings({
            @Mapping(source = "country.id", target = "countryId"),
            @Mapping(source = "country.name", target = "countryName")
    })
    FixedHolidayResponseDTO toDTO(FixedHoliday fixedHoliday);
    List<FixedHolidayResponseDTO> toDTOs(List<FixedHoliday> fixedHolidays);

}
