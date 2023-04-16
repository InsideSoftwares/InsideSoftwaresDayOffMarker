package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.entity.DayBatch;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteDayBatchList implements ItemWriter<List<DayBatch>> {

    private final BatchCreationDayService batchCreationDayService;

    @Override
    public void write(Chunk<? extends List<DayBatch>> lists)  {
        lists.forEach(batchCreationDayService::createDaysBatch);
    }
}
