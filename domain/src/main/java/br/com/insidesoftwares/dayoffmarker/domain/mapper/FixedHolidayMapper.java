package br.com.insidesoftwares.dayoffmarker.domain.mapper;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FixedHolidayMapper {
    FixedHolidayResponseDTO toDTO(FixedHoliday fixedHoliday);
    List<FixedHolidayResponseDTO> toDTOs(List<FixedHoliday> fixedHolidays);
}
