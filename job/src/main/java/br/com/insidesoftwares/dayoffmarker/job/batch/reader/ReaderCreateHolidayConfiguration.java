package br.com.insidesoftwares.dayoffmarker.job.batch.reader;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
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
public class ReaderCreateHolidayConfiguration {

	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

	@Bean("ReaderRequestUpdateHolidayStatusCreated")
	public ItemReader<Request> readerRequestUpdateHolidayStatusCreated(EntityManagerFactory entityManagerFactory) {
		JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("""
				SELECT r FROM Request r
				WHERE statusRequest = :statusRequest
				AND r.typeRequest = :typeRequest
			""");

		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("typeRequest", TypeRequest.UPDATE_HOLIDAY);
		parameterValues.put("statusRequest", StatusRequest.CREATED);
		reader.setParameterValues(parameterValues);

		reader.setPageSize(dayOffMarkerJobProperties.getSetsRequestToRunningUpdateHoliday());

		return reader;
	}

	@Bean("ReaderRequestsUpdateHoliday")
	public ItemReader<Request> readerRequestsUpdateHoliday(EntityManagerFactory entityManagerFactory) {
		JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("""
				SELECT r FROM Request r
				WHERE statusRequest = :statusRequest
				AND r.typeRequest = :typeRequest
			""");

		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("typeRequest", TypeRequest.UPDATE_HOLIDAY);
		parameterValues.put("statusRequest", StatusRequest.RUNNING);
		reader.setParameterValues(parameterValues);

		reader.setPageSize(dayOffMarkerJobProperties.getExecutesRequestsUpdateHoliday());

		return reader;
	}

	@Bean("ReaderRequestToFinalizedUpdateHoliday")
	public ItemReader<Request> readerRequestToFinalizedUpdateHoliday(EntityManagerFactory entityManagerFactory) {
		JpaPagingItemReader<Request> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("""
				SELECT r FROM Request r
				WHERE statusRequest = :statusRequest
				AND r.typeRequest = :typeRequest
			""");

		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("typeRequest", TypeRequest.UPDATE_HOLIDAY);
		parameterValues.put("statusRequest", StatusRequest.RUNNING);
		reader.setParameterValues(parameterValues);

		reader.setPageSize(dayOffMarkerJobProperties.getUpdatesRequestToFinalizedUpdateHoliday());

		return reader;
	}
}
