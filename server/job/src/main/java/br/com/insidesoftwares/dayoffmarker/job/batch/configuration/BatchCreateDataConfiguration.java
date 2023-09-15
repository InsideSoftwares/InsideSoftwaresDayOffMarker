package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.batch.listener.DayOffMarkerJobListener;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorDay;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorDayBatch;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorDayBatchStatusProcessed;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusFinalized;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusRunning;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteDay;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteDayBatch;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteDayBatchList;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteRequest;
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

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BatchCreateDataConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	@Autowired
	@Qualifier("ReaderRequestCreateDateStatusCreated")
	private ItemReader<Request> readerRequestCreateDateStatusCreated;

	@Autowired
	@Qualifier("ReaderRequestCreateDateByStatusRunning")
	private ItemReader<Request> readerRequestCreateDateByStatusRunning;

	@Autowired
	@Qualifier("UpdatesRequestToFinalizedCreateDate")
	private ItemReader<Request> updatesRequestToFinalizedCreateDate;

	@Autowired
	@Qualifier("SavesDaysCreatedCreateDate")
	private ItemReader<DayBatch> savesDaysCreatedCreateDate;

	@Autowired
	@Qualifier("UpdatesDayBatchToProcessedCreateDate")
	private ItemReader<DayBatch> updatesDayBatchToProcessedCreateDate;

    private final ProcessorRequestStatusRunning processorRequestStatusRunning;
    private final ProcessorRequestStatusFinalized processorRequestStatusFinalized;
    private final ProcessorDayBatchStatusProcessed processorDayBatchStatusProcessed;
    private final ProcessorDay processorDay;
    private final ProcessorDayBatch processorDayBatch;

    private final WriteDayBatchList writeDayBatchList;
    private final WriteDayBatch writeDayBatch;
    private final WriteDay writeDay;
    private final WriteRequest writeRequest;

	private final DayOffMarkerJobListener dayOffMarkerJobListener;
	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

    @Bean
    public Step setsRequestToRunningCreateDate () {
        return new StepBuilder("setsRequestToRunningCreateDate", jobRepository)
                .<Request, Request>chunk(dayOffMarkerJobProperties.getSetsRequestToRunningCreateDate(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerRequestCreateDateStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step executesRequestsCreateDate() {
        return new StepBuilder("executesRequestsCreateDate", jobRepository)
                .<Request, List<DayBatch>>chunk(dayOffMarkerJobProperties.getExecutesRequestsCreateDate(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerRequestCreateDateByStatusRunning)
                .processor(processorDayBatch)
                .writer(writeDayBatchList)
                .build();
    }

	@Bean
	public Step savesDaysCreatedCreateDate() {
		return new StepBuilder("savesDaysCreatedCreateDate", jobRepository)
			.<DayBatch, Day>chunk(dayOffMarkerJobProperties.getSavesDaysCreatedCreateDate(), platformTransactionManager)
			.listener(dayOffMarkerJobListener)
			.reader(savesDaysCreatedCreateDate)
			.processor(processorDay)
			.writer(writeDay)
			.build();
	}

    @Bean
    public Step updatesDayBatchToProcessedCreateDate() {
        return new StepBuilder("updatesDayBatchToProcessedCreateDate", jobRepository)
                .<DayBatch, DayBatch>chunk(dayOffMarkerJobProperties.getUpdatesDayBatchToProcessedCreateDate(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(updatesDayBatchToProcessedCreateDate)
                .processor(processorDayBatchStatusProcessed)
                .writer(writeDayBatch)
                .build();
    }

	@Bean
	public Step updatesRequestToFinalizedCreateDate() {
		return new StepBuilder("updatesRequestToFinalizedCreateDate", jobRepository)
			.<Request, Request>chunk(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedCreateDate(), platformTransactionManager)
			.listener(dayOffMarkerJobListener)
			.reader(updatesRequestToFinalizedCreateDate)
			.processor(processorRequestStatusFinalized)
			.writer(writeRequest)
			.build();
	}

    @Bean("jobCreatesDays")
    public Job jobCreatesDays() {
        return new JobBuilder("jobCreatesDays", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(dayOffMarkerJobListener)
                .start(setsRequestToRunningCreateDate())
                .next(executesRequestsCreateDate())
                .next(savesDaysCreatedCreateDate())
                .next(updatesDayBatchToProcessedCreateDate())
                .next(updatesRequestToFinalizedCreateDate())
                .build();
    }

}
