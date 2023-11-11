package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.job.batch.listener.DayOffMarkerJobListener;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorDay;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorDayBatchStatusProcessed;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteDay;
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
public class BatchCreateDataConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ProcessorDayBatchStatusProcessed processorDayBatchStatusProcessed;
    private final ProcessorDay processorDay;
    private final WriteDayBatch writeDayBatch;
    private final WriteDay writeDay;
    private final DayOffMarkerJobListener dayOffMarkerJobListener;
    private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

    @Autowired
    @Qualifier("SavesDaysCreatedCreateDate")
    private ItemReader<DayBatch> savesDaysCreatedCreateDate;
    @Autowired
    @Qualifier("UpdatesDayBatchToProcessedCreateDate")
    private ItemReader<DayBatch> updatesDayBatchToProcessedCreateDate;

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

    @Bean("jobCreatesDays")
    public Job jobCreatesDays() {
        return new JobBuilder("jobCreatesDays", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(dayOffMarkerJobListener)
                .start(savesDaysCreatedCreateDate())
                .next(updatesDayBatchToProcessedCreateDate())
                .build();
    }

}
