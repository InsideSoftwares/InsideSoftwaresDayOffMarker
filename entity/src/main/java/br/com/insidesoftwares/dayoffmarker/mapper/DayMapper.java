package br.com.insidesoftwares.dayoffmarker.mapper;

import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.entity.DayBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HolidayMapper.class, TagMapper.class})
public interface DayMapper {

    Day toDayBatch(DayBatch dayBatch);

	@Mapping(target = "holiday", expression = "java(day.isHoliday())")
	@Mapping(target = "weekend", source = "weekend")
	@Mapping(target = "holidays", source = "holidays", qualifiedByName = "toHolidayResponseDTO")
	@Mapping(target = "tags", source = "tags", qualifiedByName = "toTagResponseDTO")
	DayDTO toDayDTO(Day day);

	List<DayDTO> toDTOs(List<Day> days);

}
