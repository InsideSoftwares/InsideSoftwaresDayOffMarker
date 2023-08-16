package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessorDayBatchStatusProcessed implements ItemProcessor<DayBatch, DayBatch> {

    @Override
    public DayBatch process(DayBatch dayBatch) {
        dayBatch.setProcessed(true);
        return dayBatch;
    }

}
