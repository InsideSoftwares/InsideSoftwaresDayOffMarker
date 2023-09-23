package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@Component
@RefreshScope
public class DayOffMarkerJobProperties {

    @Value("${dom.taskexecutor.corePoolSize:5}")
    private int corePoolSize;
    @Value("${dom.taskexecutor.maxPoolSize:10}")
    private int maxPoolSize;
    @Value("${dom.taskexecutor.queueCapacity:10}")
    private int queueCapacity;


    @Value("${dom.job.chunk.requestToRunningUpdateHoliday:25}")
    private int setsRequestToRunningUpdateHoliday;
    @Value("${dom.job.chunk.requestsUpdateHoliday:25}")
    private int executesRequestsUpdateHoliday;
    @Value("${dom.job.chunk.requestToFinalizedUpdateHoliday:100}")
    private int updatesRequestToFinalizedUpdateHoliday;
    @Value("${dom.job.chunk.requestToRunningCreateDate:25}")
    private int setsRequestToRunningCreateDate;
    @Value("${dom.job.chunk.requestsCreateDate:25}")
    private int executesRequestsCreateDate;
    @Value("${dom.job.chunk.requestToFinalizedCreateDate:100}")
    private int updatesRequestToFinalizedCreateDate;
    @Value("${dom.job.chunk.daysCreatedCreateDate:100}")
    private int savesDaysCreatedCreateDate;
    @Value("${dom.job.chunk.dayBatchToProcessedCreateDate:100}")
    private int updatesDayBatchToProcessedCreateDate;
    @Value("${dom.job.chunk.processDayBatch:5000}")
    private int processDayBatch;
    @Value("${dom.job.chunk.deleteAllDaysBatch:5000}")
    private int deleteAllDaysBatch;
    @Value("${dom.job.chunk.readerLinkTagInDayStatusCreated:100}")
    private int readerLinkTagInDayStatusCreated;
    @Value("${dom.job.chunk.updatesRequestToFinalizedLinkTag:100}")
    private int updatesRequestToFinalizedLinkTag;
}
