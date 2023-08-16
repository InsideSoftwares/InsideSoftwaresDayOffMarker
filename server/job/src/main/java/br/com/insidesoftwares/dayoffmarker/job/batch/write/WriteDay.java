package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteDay implements ItemWriter<Day> {

    private final BatchCreationDayService batchCreationDayService;

    @Override
    public void write(Chunk<? extends Day> daysChunk)  {
		List<Day> days = new ArrayList<>(daysChunk.getItems());
        batchCreationDayService.createDays(days);
    }
}
