package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@StepScope
public class ReaderDayBatch implements ItemReader<DayBatch> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    private int nextRequest;
    private List<DayBatch> dayBatches;

    private List<UUID> uuid;

    @Value("#{jobParameters['List']}")
    public void setFileName(final String name) {
        uuid = new ArrayList<>();
        Arrays.stream(name.split(",")).forEach(s -> {
            uuid.add(UUID.fromString(s.trim()));
        });
    }

    @Override
    public DayBatch read() throws Exception {
        if(dayBatches == null){
            dayBatches = batchCreationDayService.findAllDayBatchForBatch(
                    uuid
            );
        }
        DayBatch nextDayBatch = null;

        if (nextRequest < dayBatches.size()) {
            nextDayBatch = dayBatches.get(nextRequest++);
        } else {
            nextRequest = 0;
        }

        return nextDayBatch;
    }
}
