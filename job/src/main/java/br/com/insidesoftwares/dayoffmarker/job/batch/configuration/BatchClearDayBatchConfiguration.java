package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.job.batch.listener.DayOffMarkerJobListener;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessDayBatchNotProcessed;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.DeleteDayBatchList;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteDayBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchClearDayBatchConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final DayOffMarkerJobListener dayOffMarkerJobListener;
	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;
    private final ProcessDayBatchNotProcessed processDayBatchNotProcessed;
	private final WriteDayBatch writeDayBatch;
	private final DeleteDayBatchList deleteDayBatchList;

	@Autowired
	@Qualifier("ReaderDayBatchNotProcessed")
	private ItemReader<DayBatch> readerDayBatchNotProcessed;

	@Autowired
	@Qualifier("ReaderDayBatchProcessed")
	private ItemReader<DayBatch> readerDayBatchProcessed;


    @Bean
    public Step processDayBatch() {
        return new StepBuilder("processDayBatch", jobRepository)
                .<DayBatch, DayBatch>chunk(dayOffMarkerJobProperties.getProcessDayBatch(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerDayBatchNotProcessed)
                .processor(processDayBatchNotProcessed)
                .writer(writeDayBatch)
                .build();
    }

    @Bean
    public Step deleteAllDaysBatch() {
        return new StepBuilder("deleteAllDaysBatch", jobRepository)
                .<DayBatch, DayBatch>chunk(dayOffMarkerJobProperties.getDeleteAllDaysBatch(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerDayBatchProcessed)
                .writer(deleteDayBatchList)
                .build();
    }

    @Bean("jobClearDayBatch")
    public Job jobClearDayBatch() {
        return new JobBuilder("jobClearDayBatch", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(dayOffMarkerJobListener)
                .start(processDayBatch())
                .next(deleteAllDaysBatch())
                .build();
    }

}
