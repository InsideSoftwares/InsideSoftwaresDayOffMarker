package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.DayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessorDay implements ItemProcessor<DayBatch, Day> {

    private final DayMapper dayMapper;

    @Override
    public Day process(final DayBatch dayBatch) {
        Day day = dayMapper.toDayBatch(dayBatch);
		day.setDayOfWeek(day.getDate().getDayOfWeek());
		day.setDayOfYear(day.getDate().getDayOfYear());
		return day;
    }

}
