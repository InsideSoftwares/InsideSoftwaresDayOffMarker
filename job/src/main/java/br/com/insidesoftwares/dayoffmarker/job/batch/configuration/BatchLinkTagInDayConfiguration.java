package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayTag;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.batch.listener.DayOffMarkerJobListener;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorLinkTag;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusFinalized;
import br.com.insidesoftwares.dayoffmarker.job.batch.process.ProcessorRequestStatusRunning;
import br.com.insidesoftwares.dayoffmarker.job.batch.write.WriteLinkTag;
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
public class BatchLinkTagInDayConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	@Autowired
	@Qualifier("ReaderLinkTagInDayStatusCreated")
	private ItemReader<Request> readerLinkTagInDayStatusCreated;
	@Autowired
	@Qualifier("ReaderRequestsLinkTag")
	private ItemReader<Request> readerRequestsLinkTag;

	@Autowired
	@Qualifier("ReaderRequestToFinalizedLinkTag")
	private ItemReader<Request> readerRequestToFinalizedLinkTag;

	private final ProcessorRequestStatusRunning processorRequestStatusRunning;
	private final ProcessorLinkTag processorLinkTag;
	private final ProcessorRequestStatusFinalized processorRequestStatusFinalized;

	private final WriteLinkTag writeLinkTag;
	private final WriteRequest writeRequest;

	private final DayOffMarkerJobListener dayOffMarkerJobListener;
	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

    @Bean("setsRequestToRunningLinkTag")
    public Step setsRequestToRunningLinkTag () {
        return new StepBuilder("setsRequestToRunningLinkTag", jobRepository)
                .<Request, Request>chunk(dayOffMarkerJobProperties.getSetsRequestToRunningCreateDate(), platformTransactionManager)
				.listener(dayOffMarkerJobListener)
                .reader(readerLinkTagInDayStatusCreated)
                .processor(processorRequestStatusRunning)
                .writer(writeRequest)
                .build();
    }

	@Bean("executesRequestsLinkTag")
	public Step executesRequestsLinkTag() {
		return new StepBuilder("executesRequestsLinkTag", jobRepository)
			.<Request, List<DayTag>>chunk(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedUpdateHoliday(), platformTransactionManager)
			.listener(dayOffMarkerJobListener)
			.reader(readerRequestsLinkTag)
			.processor(processorLinkTag)
			.writer(writeLinkTag)
			.build();
	}

	@Bean("updatesRequestToFinalizedLinkTag")
	public Step updatesRequestToFinalizedLinkTag() {
		return new StepBuilder("updatesRequestToFinalizedLinkTag", jobRepository)
			.<Request, Request>chunk(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedUpdateHoliday(), platformTransactionManager)
			.listener(dayOffMarkerJobListener)
			.reader(readerRequestToFinalizedLinkTag)
			.processor(processorRequestStatusFinalized)
			.writer(writeRequest)
			.build();
	}

    @Bean("jobLinkTagInDay")
    public Job jobLinkTagInDay() {
        return new JobBuilder("jobLinkTagInDay", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(dayOffMarkerJobListener)
                .start(setsRequestToRunningLinkTag())
                .next(executesRequestsLinkTag())
                .next(updatesRequestToFinalizedLinkTag())
                .build();
    }

}
