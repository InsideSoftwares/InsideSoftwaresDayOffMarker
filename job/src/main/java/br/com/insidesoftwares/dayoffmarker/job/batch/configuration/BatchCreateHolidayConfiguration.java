package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.job.batch.listener.DayOffMarkerJobListener;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessHoliday;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusFinalized;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusRunning;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteHolidayRequestDTOList;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteRequest;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
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
public class BatchCreateHolidayConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	@Autowired
	@Qualifier("ReaderRequestUpdateHolidayStatusCreated")
	private ItemReader<Request> readerRequestUpdateHolidayStatusCreated;

	@Autowired
	@Qualifier("ReaderRequestsUpdateHoliday")
	private ItemReader<Request> readerRequestsUpdateHoliday;

	@Autowired
	@Qualifier("ReaderRequestToFinalizedUpdateHoliday")
	private ItemReader<Request> readerRequestToFinalizedUpdateHoliday;

    private final ProcessorRequestStatusRunning processorRequestStatusRunning;
    private final ProcessorRequestStatusFinalized processorRequestStatusFinalized;
    private final ProcessHoliday processHoliday;

    private final WriteHolidayRequestDTOList writeHolidayRequestDTOList;
    private final WriteRequest writeRequest;

	private final DayOffMarkerJobListener dayOffMarkerJobListener;
	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

    @Bean
    public Step setsRequestToRunningUpdateHoliday () {
        return new StepBuilder("setsRequestToRunningUpdateHoliday", jobRepository)
                .<Request, Request>chunk(dayOffMarkerJobProperties.getSetsRequestToRunningUpdateHoliday(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerRequestUpdateHolidayStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

    @Bean
    public Step executesRequestsUpdateHoliday() {
        return new StepBuilder("executesRequestsUpdateHoliday", jobRepository)
                .<Request, List<HolidayCreateRequestDTO>>chunk(dayOffMarkerJobProperties.getExecutesRequestsUpdateHoliday(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerRequestsUpdateHoliday)
                .processor(processHoliday)
                .writer(writeHolidayRequestDTOList)
                .build();
    }

    @Bean
    public Step updatesRequestToFinalizedUpdateHoliday() {
        return new StepBuilder("updatesRequestToFinalizedUpdateHoliday", jobRepository)
                .<Request, Request>chunk(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedUpdateHoliday(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerRequestToFinalizedUpdateHoliday)
                .processor(processorRequestStatusFinalized)
                .writer(writeRequest)
                .build();
    }

    @Bean("jobCreateHoliday")
    public Job jobCreateHoliday() {
        return new JobBuilder("jobCreateHoliday", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(dayOffMarkerJobListener)
                .start(setsRequestToRunningUpdateHoliday())
                .next(executesRequestsUpdateHoliday())
                .next(updatesRequestToFinalizedUpdateHoliday())
                .build();
    }

}
