package br.com.sawcunha.dayoffmarker.batch.configuration;

import br.com.sawcunha.dayoffmarker.batch.listener.JobCreateDaysListener;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessHoliday;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorRequestStatusFinalized;
import br.com.sawcunha.dayoffmarker.batch.process.ProcessorRequestStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestDTOStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestStatusRunning;
import br.com.sawcunha.dayoffmarker.batch.reader.ReaderRequestUpdateHolidayStatusCreated;
import br.com.sawcunha.dayoffmarker.batch.write.WriteHolidayRequestDTOList;
import br.com.sawcunha.dayoffmarker.batch.write.WriteRequest;
import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
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
public class BatchUpdateHolidayConfiguration {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final ReaderRequestUpdateHolidayStatusCreated readerRequestUpdateHolidayStatusCreated;
    private final ReaderRequestDTOStatusRunning readerRequestDTOStatusRunning;
    private final ReaderRequestStatusRunning readerRequestStatusRunning;

    private final ProcessorRequestStatusRunning processorRequestStatusRunning;
    private final ProcessorRequestStatusFinalized processorRequestStatusFinalized;
    private final ProcessHoliday processHoliday;

    private final WriteHolidayRequestDTOList writeHolidayRequestDTOList;
    private final WriteRequest writeRequest;

    @Bean
    public Step setsRequestToRunningUpdateHoliday () {
        return this.stepBuilderFactory.get("setsRequestToRunningUpdateHoliday")
                .<Request, Request>chunk(25)
				.listener(jobCreateDaysListenerUpdateHoliday())
                .reader(readerRequestUpdateHolidayStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step executesRequestsUpdateHoliday() {
        return this.stepBuilderFactory.get("executesRequestsUpdateHoliday")
                .<RequestDTO, List<HolidayRequestDTO>>chunk(25)
				.listener(jobCreateDaysListenerUpdateHoliday())
                .reader(readerRequestDTOStatusRunning)
                .processor(processHoliday)
                .writer(writeHolidayRequestDTOList)
                .build();
    }

    @Bean
    public Step updatesRequestToFinalizedUpdateHoliday() {
        return this.stepBuilderFactory.get("updatesRequestToFinalizedUpdateHoliday")
                .<Request, Request>chunk(25)
				.listener(jobCreateDaysListenerUpdateHoliday())
                .reader(readerRequestStatusRunning)
                .processor(processorRequestStatusFinalized)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public JobCreateDaysListener jobCreateDaysListenerUpdateHoliday(){
        return new JobCreateDaysListener();
    }

    @Bean("jobUpdateHoliday")
    public Job jobUpdateHoliday() {
        return this.jobBuilderFactory.get("jobCreatesDays")
                .incrementer(new RunIdIncrementer())
                .listener(jobCreateDaysListenerUpdateHoliday())
                .start(setsRequestToRunningUpdateHoliday())
                .next(executesRequestsUpdateHoliday())
                .next(updatesRequestToFinalizedUpdateHoliday())
                .build();
    }

}
