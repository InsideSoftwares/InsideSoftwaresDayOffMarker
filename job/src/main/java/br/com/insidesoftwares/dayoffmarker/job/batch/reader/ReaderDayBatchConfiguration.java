package br.com.insidesoftwares.dayoffmarker.job.batch.reader;

import br.com.insidesoftwares.dayoffmarker.entity.DayBatch;
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
public class ReaderDayBatchConfiguration {

	private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

	@Bean("ReaderDayBatchProcessed")
	public ItemReader<DayBatch> readerDayBatchProcessed(EntityManagerFactory entityManagerFactory) {
		JpaPagingItemReader<DayBatch> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("""
				SELECT r FROM DayBatch r
				WHERE isProcessed = :isProcessed
			""");

		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("isProcessed", Boolean.TRUE);
		reader.setParameterValues(parameterValues);

		reader.setPageSize(dayOffMarkerJobProperties.getProcessDayBatch());

		return reader;
	}

	@Bean("ReaderDayBatchNotProcessed")
	public ItemReader<DayBatch> readerDayBatchNotProcessed(EntityManagerFactory entityManagerFactory) {
		JpaPagingItemReader<DayBatch> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("""
				SELECT r FROM DayBatch r
				WHERE isProcessed = :isProcessed
			""");

		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("isProcessed", Boolean.FALSE);
		reader.setParameterValues(parameterValues);

		reader.setPageSize(dayOffMarkerJobProperties.getDeleteAllDaysBatch());

		return reader;
	}
}
