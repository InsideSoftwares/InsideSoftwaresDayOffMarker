package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayTag;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.batch.listener.DayOffMarkerJobListener;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusFinalized;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusRunning;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorUnlinkTag;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteRequest;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteUnlinkTag;
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
public class BatchUnlinkTagInDayConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	@Autowired
	@Qualifier("ReaderUnlinkTagInDayStatusCreated")
	private ItemReader<Request> readerUnlinkTagInDayStatusCreated;
	@Autowired
	@Qualifier("ReaderRequestsUnlinkTag")
	private ItemReader<Request> readerRequestsUnlinkTag;

	@Autowired
	@Qualifier("ReaderRequestToFinalizedUnlinkTag")
	private ItemReader<Request> readerRequestToFinalizedUnlinkTag;

	private final ProcessorRequestStatusRunning processorRequestStatusRunning;
	private final ProcessorUnlinkTag processorUnlinkTag;
	private final ProcessorRequestStatusFinalized processorRequestStatusFinalized;

	private final WriteUnlinkTag writeUnlinkTag;
	private final WriteRequest writeRequest;

	private final DayOffMarkerJobListener dayOffMarkerJobListener;
	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

    @Bean("setsRequestToRunningUnlinkTag")
    public Step setsRequestToRunningUnlinkTag () {
        return new StepBuilder("setsRequestToRunningUnlinkTag", jobRepository)
                .<Request, Request>chunk(dayOffMarkerJobProperties.getSetsRequestToRunningCreateDate(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerUnlinkTagInDayStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

	@Bean("executesRequestsUnlinkTag")
	public Step executesRequestsUnlinkTag() {
		return new StepBuilder("executesRequestsUnlinkTag", jobRepository)
			.<Request, List<DayTag>>chunk(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedUpdateHoliday(), platformTransactionManager)
			.listener(dayOffMarkerJobListener)
			.reader(readerRequestsUnlinkTag)
			.processor(processorUnlinkTag)
			.writer(writeUnlinkTag)
			.build();
	}

	@Bean("updatesRequestToFinalizedUnlinkTag")
	public Step updatesRequestToFinalizedUnlinkTag() {
		return new StepBuilder("updatesRequestToFinalizedUnlinkTag", jobRepository)
			.<Request, Request>chunk(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedUpdateHoliday(), platformTransactionManager)
			.listener(dayOffMarkerJobListener)
			.reader(readerRequestToFinalizedUnlinkTag)
			.processor(processorRequestStatusFinalized)
			.writer(writeRequest)
			.build();
	}

    @Bean("jobUnlinkTagInDay")
    public Job jobUnlinkTagInDay() {
        return new JobBuilder("jobUnlinkTagInDay", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(dayOffMarkerJobListener)
                .start(setsRequestToRunningUnlinkTag())
                .next(executesRequestsUnlinkTag())
                .next(updatesRequestToFinalizedUnlinkTag())
                .build();
    }

}
