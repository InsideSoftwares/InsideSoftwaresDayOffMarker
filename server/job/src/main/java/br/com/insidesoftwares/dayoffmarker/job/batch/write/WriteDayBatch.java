package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteDayBatch implements ItemWriter<DayBatch> {

    private final BatchCreationDayService batchCreationDayService;

	@Override
	public void write(Chunk<? extends DayBatch> daysBatchChunk) {
		List<DayBatch> daysBatch = new ArrayList<>(daysBatchChunk.getItems());
		batchCreationDayService.createDaysBatch(daysBatch);
	}
}
