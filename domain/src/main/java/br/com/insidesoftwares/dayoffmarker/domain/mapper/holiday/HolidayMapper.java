package br.com.insidesoftwares.dayoffmarker.domain.mapper.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    @Mapping(source = "day.date", target = "day")
    HolidayResponseDTO toDTO(Holiday holiday);

    default List<HolidayResponseDTO> toDTOs(List<Holiday> holidays) {
        return holidays.stream().map(this::toDTO).toList();
    }

    @Mapping(target = "day", ignore = true)
    HolidayResponseDTO toHolidayResponseDTO(Holiday holiday);

    @Named("toHolidayResponseDTO")
    default Set<HolidayResponseDTO> toHolidayResponseDTO(Set<Holiday> holidays) {
        return holidays.stream().map(this::toHolidayResponseDTO).collect(Collectors.toSet());
    }

    HolidayRequestDTO toHolidayResponseDTO(HolidayBatchRequestDTO holidayBatchRequestDTO, UUID dayId);

    @Mapping(target = "id", source = "fixedHoliday.id", ignore = true)
    @Mapping(target = "nationalHoliday", constant = "true")
    @Mapping(target = "automaticUpdate", constant = "true")
    @Mapping(target = "fixedHolidayID", source = "fixedHoliday.id")
    @Mapping(target = "day", source = "day")
    @Mapping(target = "holidayType", source = "fixedHoliday", qualifiedByName = "getTypeHoliday")

    Holiday toEntity(final FixedHoliday fixedHoliday, final Day day);

    @Named("getTypeHoliday")
    default TypeHoliday getTypeHoliday(final FixedHoliday fixedHoliday) {
        if (Objects.isNull(fixedHoliday.getFromTime()) && fixedHoliday.isOptional()) {
            return TypeHoliday.OPTIONAL;
        }
        return Objects.nonNull(fixedHoliday.getFromTime()) && fixedHoliday.isOptional() ? TypeHoliday.HALF_PERIOD : TypeHoliday.MANDATORY;
    }
}
