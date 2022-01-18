package br.com.sawcunha.dayoffmarker.batch.write;

import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WriteDay implements ItemWriter<Day> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    @Override
    public void write(List<? extends Day> days)  {
        days.forEach(day -> batchCreationDayService.createDay(day));
    }
}
