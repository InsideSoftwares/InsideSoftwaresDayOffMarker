package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProcessDayBatchNotProcessed implements ItemProcessor<DayBatch, DayBatch> {

	private final DayService dayService;

    @Override
    public DayBatch process(DayBatch dayBatch) {
        Day day = dayService.findDayByDate(dayBatch.getDate());
		if(Objects.nonNull(day)) {
			dayBatch.setProcessed(true);
		}
		return dayBatch;
    }

}
