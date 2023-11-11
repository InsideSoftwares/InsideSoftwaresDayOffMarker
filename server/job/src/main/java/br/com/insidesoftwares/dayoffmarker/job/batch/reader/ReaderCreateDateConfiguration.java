package br.com.insidesoftwares.dayoffmarker.job.batch.reader;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
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
