package br.com.insidesoftwares.dayoffmarker.domain.mapper.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FixedHolidayMapper {
    FixedHoliday toEntity(FixedHolidayRequestDTO fixedHolidayRequestDTO);
    FixedHolidayResponseDTO toDTO(FixedHoliday fixedHoliday);

    List<FixedHolidayResponseDTO> toDTOs(List<FixedHoliday> fixedHolidays);
}
