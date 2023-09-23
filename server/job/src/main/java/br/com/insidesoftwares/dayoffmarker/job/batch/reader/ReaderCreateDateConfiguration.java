package br.com.insidesoftwares.dayoffmarker.job.batch.reader;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.batch.configuration.DayOffMarkerJobProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ReaderCreateDateConfiguration {

    private final DayOffMarkerJobProperties dayOffMarkerJobProperties;
    private final EntityManagerFactory entityManagerFactory;

    @Bean("ReaderRequestCreateDateStatusCreated")
    public ItemReader<Request> readerRequestCreateDateStatusCreated() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.CREATE_DATE);
        parameterValues.put("statusRequest", StatusRequest.CREATED);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getSetsRequestToRunningCreateDate());

        return reader;
    }

    @Bean("ReaderRequestCreateDateByStatusRunning")
    public ItemReader<Request> readerRequestCreateDateByStatusRunning() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.CREATE_DATE);
        parameterValues.put("statusRequest", StatusRequest.RUNNING);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getExecutesRequestsCreateDate());

        return reader;
    }

    @Bean("UpdatesRequestToFinalizedCreateDate")
    public ItemReader<Request> updatesRequestToFinalizedCreateDate() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.CREATE_DATE);
        parameterValues.put("statusRequest", StatusRequest.RUNNING);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedCreateDate());

        return reader;
    }

    @Bean("SavesDaysCreatedCreateDate")
    public ItemReader<DayBatch> savesDaysCreatedCreateDate() {
        JpaPagingItemReader<DayBatch> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT d FROM DayBatch d INNER JOIN Request r ON d.requestID = r.id
                	WHERE r.typeRequest = :typeRequest
                	AND d.isProcessed = :processed
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.CREATE_DATE);
        parameterValues.put("processed", false);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getSavesDaysCreatedCreateDate());

        return reader;
    }

    @Bean("UpdatesDayBatchToProcessedCreateDate")
    public ItemReader<DayBatch> updatesDayBatchToProcessedCreateDate() {
        JpaPagingItemReader<DayBatch> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT d FROM DayBatch d INNER JOIN Request r ON d.requestID = r.id
                	WHERE r.typeRequest = :typeRequest
                	AND d.isProcessed = :processed
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.CREATE_DATE);
        parameterValues.put("processed", false);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getUpdatesDayBatchToProcessedCreateDate());

        return reader;
    }
}
