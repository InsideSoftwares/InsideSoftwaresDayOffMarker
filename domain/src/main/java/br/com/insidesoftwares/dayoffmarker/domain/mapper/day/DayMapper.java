package br.com.insidesoftwares.dayoffmarker.domain.mapper.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.holiday.HolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.tag.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HolidayMapper.class, TagMapper.class})
public interface DayMapper {

    @Mapping(target = "holiday", expression = "java(day.isHoliday())")
    @Mapping(target = "weekend", source = "weekend")
    @Mapping(target = "holidays", source = "holidays", qualifiedByName = "toHolidayResponseDTO")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTagResponseDTO")
    DayDTO toDayDTO(Day day);

    List<DayDTO> toDTOs(List<Day> days);

}
