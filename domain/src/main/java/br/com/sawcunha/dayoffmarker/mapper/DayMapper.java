package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DayMapper {

    Day toDayBatch(DayBatch dayBatch);

}
