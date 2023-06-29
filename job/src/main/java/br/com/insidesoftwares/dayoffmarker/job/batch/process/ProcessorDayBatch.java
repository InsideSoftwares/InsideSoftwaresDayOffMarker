package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.entity.DayBatch;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessorDayBatch implements ItemProcessor<Request, List<DayBatch>> {

    private final BatchCreationDayService batchCreationDayService;

    @Override
    public List<DayBatch> process(Request request) {
        List<DayBatch> dayBatches = new ArrayList<>();
        Integer year = RequestParametersUtils.getYear(request.getRequestParameter());
        Integer month = RequestParametersUtils.getMonth(request.getRequestParameter());

        LocalDate dateBase = LocalDate.of(year,month,1);

        while (dateBase.getYear() == year && dateBase.getMonthValue() == month){
			if(!batchCreationDayService.existDayInDayBatch(dateBase)) {
				DayBatch dayBatch = DayBatch.builder()
					.date(dateBase)
					.isProcessed(false)
					.isWeekend(DateUtils.isWeenkend(dateBase))
					.isHoliday(false)
					.requestID(request.getId())
					.build();

				dayBatches.add(dayBatch);
			}
            dateBase = dateBase.plusDays(1);
        }

        return dayBatches;
    }

}
