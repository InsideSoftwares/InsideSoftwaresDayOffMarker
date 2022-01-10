package br.com.sawcunha.dayoffmarker.batch.process;

import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProcessorDayBatchStatusProcessed implements ItemProcessor<DayBatch, DayBatch> {

    @Override
    public DayBatch process(DayBatch dayBatch) throws Exception {
        dayBatch.setProcessed(true);
        return dayBatch;
    }

}
