package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class ReaderDayBatch implements ItemReader<DayBatch> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    private int nextRequest;
    private List<DayBatch> dayBatches;
	private Long jobId;

	@BeforeStep
	public void getInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		this.jobId = jobExecution.getJobId();
	}
    @Override
    public DayBatch read() throws Exception {
        if(dayBatches == null){
            dayBatches = batchCreationDayService.findAllDayBatchForBatch(jobId);
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
