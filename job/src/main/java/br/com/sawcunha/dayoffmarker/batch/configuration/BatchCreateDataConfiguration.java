package br.com.sawcunha.dayoffmarker.batch.configuration;

import br.com.sawcunha.dayoffmarker.batch.listener.JobCreateDaysListener;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorDay;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorDayBatch;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorDayBatchStatusProcessed;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorRequestStatusFinalized;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorRequestStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderDayBatch;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestDTOStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestStatusCreated;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.write.WriteDay;
import br.com.sawcunha.dayoffmarker.batch.write.WriteDayBatch;
import br.com.sawcunha.dayoffmarker.batch.write.WriteDayBatchList;
import br.com.sawcunha.dayoffmarker.batch.write.WriteRequest;
import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.entity.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchCreateDataConfiguration {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    private final JobRepository jobRepository;

    private final ReaderDayBatch readerDayBatch;
    private final ReaderRequestStatusCreated readerRequestStatusCreated;
    private final ReaderRequestDTOStatusRunning readerRequestDTOStatusRunning;
    private final ReaderRequestStatusRunning readerRequestStatusRunning;

    private final ProcessorRequestStatusRunning processorRequestStatusRunning;
    private final ProcessorRequestStatusFinalized processorRequestStatusFinalized;
    private final ProcessorDayBatchStatusProcessed processorDayBatchStatusProcessed;
    private final ProcessorDay processorDay;
    private final ProcessorDayBatch processorDayBatch;

    private final WriteDayBatchList writeDayBatchList;
    private final WriteDayBatch writeDayBatch;
    private final WriteDay writeDay;
    private final WriteRequest writeRequest;

    @Bean
    public Step setsRequestToRunning () {
        return this.stepBuilderFactory.get("setsRequestToRunning")
                .<Request, Request>chunk(25)
				.listener(jobCreateDaysListener())
                .reader(readerRequestStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step executesRequests() {
        return this.stepBuilderFactory.get("executesRequests")
                .<RequestDTO, List<DayBatch>>chunk(25)
				.listener(jobCreateDaysListener())
                .reader(readerRequestDTOStatusRunning)
                .processor(processorDayBatch)
                .writer(writeDayBatchList)
                .build();
    }

    @Bean
    public Step updatesRequestToFinalized() {
        return this.stepBuilderFactory.get("updatesRequestToFinalized")
                .<Request, Request>chunk(25)
				.listener(jobCreateDaysListener())
                .reader(readerRequestStatusRunning)
                .processor(processorRequestStatusFinalized)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step savesDaysCreated() {
        return this.stepBuilderFactory.get("savesDaysCreated")
                .<DayBatch, Day>chunk(100)
				.listener(jobCreateDaysListener())
                .reader(readerDayBatch)
                .processor(processorDay)
                .writer(writeDay)
                .build();
    }

    @Bean
    public Step updatesDayBatchToProcessed() {
        return this.stepBuilderFactory.get("updatesDayBatchToProcessed")
                .<DayBatch, DayBatch>chunk(100)
				.listener(jobCreateDaysListener())
                .reader(readerDayBatch)
                .processor(processorDayBatchStatusProcessed)
                .writer(writeDayBatch)
                .build();
    }

    @Bean
    public JobCreateDaysListener jobCreateDaysListener(){
        return new JobCreateDaysListener();
    }

    @Bean("jobCreatesDays")
    public Job jobCreatesDays() {
        return this.jobBuilderFactory.get("jobCreatesDays")
                .incrementer(new RunIdIncrementer())
                .listener(jobCreateDaysListener())
                .start(setsRequestToRunning())
                .next(executesRequests())
                .next(savesDaysCreated())
                .next(updatesDayBatchToProcessed())
                .next(updatesRequestToFinalized())
                .build();
    }

    @Bean
    public JobLauncher jobLaucherCreatesDays() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
