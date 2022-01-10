package br.com.sawcunha.dayoffmarker.batch.configuration;

import br.com.sawcunha.dayoffmarker.batch.listener.JobCompletionListener;
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
public class BatchConfiguration {

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
    public Step defineRequestParaRunning() {
        return this.stepBuilderFactory.get("defineRequestParaRunning")
                .<Request, Request>chunk(10)
                .reader(readerRequestStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step executaOsRequests() {
        return this.stepBuilderFactory.get("executaOsRequests")
                .<RequestDTO, List<DayBatch>>chunk(10)
                .reader(readerRequestDTOStatusRunning)
                .processor(processorDayBatch)
                .writer(writeDayBatchList)
                .build();
    }

    @Bean
    public Step atualizaOsRequestParaFinalizado() {
        return this.stepBuilderFactory.get("atualizaOsRequestParaFinalizado")
                .<Request, Request>chunk(10)
                .reader(readerRequestStatusRunning)
                .processor(processorRequestStatusFinalized)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step salvaOsDiasCriados() {

        return this.stepBuilderFactory.get("salvaOsDiasCriados")
                .<DayBatch, Day>chunk(31)
                .reader(readerDayBatch)
                .processor(processorDay)
                .writer(writeDay)
                .build();
    }

    @Bean
    public Step atualizaOsDayBatchParaProcessado() {

        return this.stepBuilderFactory.get("atualizaOsDayBatchParaProcessado")
                .<DayBatch, DayBatch>chunk(31)
                .reader(readerDayBatch)
                .processor(processorDayBatchStatusProcessed)
                .writer(writeDayBatch)
                .build();
    }

    @Bean
    public JobCompletionListener jobCompletionListener(){
        return new JobCompletionListener();
    }

    @Bean
    public Job customerUpdateJob(
            JobCompletionListener listener
    ) {
        return this.jobBuilderFactory.get("customerUpdateJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(defineRequestParaRunning())
                .next(executaOsRequests())
                .next(salvaOsDiasCriados())
                .next(atualizaOsDayBatchParaProcessado())
                .next(atualizaOsRequestParaFinalizado())
                .build();
    }

    @Bean
    public JobLauncher jobLoteMovimientosLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
