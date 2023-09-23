package br.com.insidesoftwares.dayoffmarker.domain.mapper;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    @Mapping(source = "day.date", target = "day")
    HolidayResponseDTO toDTO(Holiday holiday);

    default List<HolidayResponseDTO> toDTOs(List<Holiday> holidays) {
        return holidays.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Mapping(target = "day", ignore = true)
    HolidayResponseDTO toHolidayResponseDTO(Holiday holiday);

    @Named("toHolidayResponseDTO")
    default Set<HolidayResponseDTO> toHolidayResponseDTO(Set<Holiday> holidays) {
        return holidays.stream().map(this::toHolidayResponseDTO).collect(Collectors.toSet());
    }

    HolidayRequestDTO toHolidayResponseDTO(HolidayBatchRequestDTO holidayBatchRequestDTO, Long dayId);

}
