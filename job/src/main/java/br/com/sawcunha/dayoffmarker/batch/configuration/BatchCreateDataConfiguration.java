package br.com.sawcunha.dayoffmarker.batch.configuration;

import br.com.sawcunha.dayoffmarker.batch.listener.JobCreateDaysListener;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorDay;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorDayBatch;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorDayBatchStatusProcessed;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorRequestStatusFinalized;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorRequestStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderDayBatch;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestCreateDateStatusCreated;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestDTOStatusRunning;
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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchCreateDataConfiguration {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final ReaderDayBatch readerDayBatch;
    private final ReaderRequestCreateDateStatusCreated readerRequestCreateDateStatusCreated;
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
    public Step setsRequestToRunningCreateDate () {
        return this.stepBuilderFactory.get("setsRequestToRunningCreateDate")
                .<Request, Request>chunk(25)
				.listener(jobCreateDaysListenerCreateDate())
                .reader(readerRequestCreateDateStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step executesRequestsCreateDate() {
        return this.stepBuilderFactory.get("executesRequestsCreateDate")
                .<RequestDTO, List<DayBatch>>chunk(25)
				.listener(jobCreateDaysListenerCreateDate())
                .reader(readerRequestDTOStatusRunning)
                .processor(processorDayBatch)
                .writer(writeDayBatchList)
                .build();
    }

    @Bean
    public Step updatesRequestToFinalizedCreateDate() {
        return this.stepBuilderFactory.get("updatesRequestToFinalizedCreateDate")
                .<Request, Request>chunk(25)
				.listener(jobCreateDaysListenerCreateDate())
                .reader(readerRequestStatusRunning)
                .processor(processorRequestStatusFinalized)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step savesDaysCreatedCreateDate() {
        return this.stepBuilderFactory.get("savesDaysCreatedCreateDate")
                .<DayBatch, Day>chunk(100)
				.listener(jobCreateDaysListenerCreateDate())
                .reader(readerDayBatch)
                .processor(processorDay)
                .writer(writeDay)
                .build();
    }

    @Bean
    public Step updatesDayBatchToProcessedCreateDate() {
        return this.stepBuilderFactory.get("updatesDayBatchToProcessedCreateDate")
                .<DayBatch, DayBatch>chunk(100)
				.listener(jobCreateDaysListenerCreateDate())
                .reader(readerDayBatch)
                .processor(processorDayBatchStatusProcessed)
                .writer(writeDayBatch)
                .build();
    }

    @Bean
    public JobCreateDaysListener jobCreateDaysListenerCreateDate(){
        return new JobCreateDaysListener();
    }

    @Bean("jobCreatesDays")
    public Job jobCreatesDays() {
        return this.jobBuilderFactory.get("jobCreatesDays")
                .incrementer(new RunIdIncrementer())
                .listener(jobCreateDaysListenerCreateDate())
                .start(setsRequestToRunningCreateDate())
                .next(executesRequestsCreateDate())
                .next(savesDaysCreatedCreateDate())
                .next(updatesDayBatchToProcessedCreateDate())
                .next(updatesRequestToFinalizedCreateDate())
                .build();
    }

}
