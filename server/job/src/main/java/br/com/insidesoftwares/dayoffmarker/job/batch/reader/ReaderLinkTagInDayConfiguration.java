package br.com.insidesoftwares.dayoffmarker.job.batch.reader;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
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
public class ReaderLinkTagInDayConfiguration {

    private final DayOffMarkerJobProperties dayOffMarkerJobProperties;
    private final EntityManagerFactory entityManagerFactory;

    @Bean("ReaderLinkTagInDayStatusCreated")
    public ItemReader<Request> readerLinkTagInDayStatusCreated() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.LINK_TAG);
        parameterValues.put("statusRequest", StatusRequest.CREATED);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getReaderLinkTagInDayStatusCreated());

        return reader;
    }

    @Bean("ReaderRequestsLinkTag")
    public ItemReader<Request> readerRequestsLinkTag() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.LINK_TAG);
        parameterValues.put("statusRequest", StatusRequest.RUNNING);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getExecutesRequestsUpdateHoliday());

        return reader;
    }

    @Bean("ReaderRequestToFinalizedLinkTag")
    public ItemReader<Request> ReaderRequestToFinalizedLinkTag() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.LINK_TAG);
        parameterValues.put("statusRequest", StatusRequest.RUNNING);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedLinkTag());

        return reader;
    }
}
