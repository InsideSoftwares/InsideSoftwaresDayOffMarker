package br.com.sawcunha.dayoffmarker.batch.process;

import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.mapper.DayMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessorDay implements ItemProcessor<DayBatch, Day> {

    @Autowired
    private DayMapper dayMapper;

    @Override
    public Day process(final DayBatch dayBatch) {
        return dayMapper.toDayBatch(dayBatch);
    }

}
