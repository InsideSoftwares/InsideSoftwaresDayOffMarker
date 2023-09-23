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
public class ReaderUnlinkTagInDayConfiguration {

    private final DayOffMarkerJobProperties dayOffMarkerJobProperties;
    private final EntityManagerFactory entityManagerFactory;

    @Bean("ReaderUnlinkTagInDayStatusCreated")
    public ItemReader<Request> readerUnlinkTagInDayStatusCreated() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.UNLINK_TAG);
        parameterValues.put("statusRequest", StatusRequest.CREATED);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getReaderLinkTagInDayStatusCreated());

        return reader;
    }

    @Bean("ReaderRequestsUnlinkTag")
    public ItemReader<Request> readerRequestsUnlinkTag() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.UNLINK_TAG);
        parameterValues.put("statusRequest", StatusRequest.RUNNING);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getExecutesRequestsUpdateHoliday());

        return reader;
    }

    @Bean("ReaderRequestToFinalizedUnlinkTag")
    public ItemReader<Request> readerRequestToFinalizedUnlinkTag() {
        JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
                	SELECT r FROM Request r
                	WHERE statusRequest = :statusRequest
                	AND r.typeRequest = :typeRequest
                """);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("typeRequest", TypeRequest.UNLINK_TAG);
        parameterValues.put("statusRequest", StatusRequest.RUNNING);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedLinkTag());

        return reader;
    }
}
