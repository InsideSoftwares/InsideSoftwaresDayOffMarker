package br.com.sawcunha.dayoffmarker.batch.write;

import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WriteDayBatchList implements ItemWriter<List<DayBatch>> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    @Override
    public void write(List<? extends List<DayBatch>> lists)  {
        lists.forEach(dayBatches -> dayBatches.forEach(dayBatch -> {
            batchCreationDayService.createDayBatch(dayBatch);
        }));
    }
}
