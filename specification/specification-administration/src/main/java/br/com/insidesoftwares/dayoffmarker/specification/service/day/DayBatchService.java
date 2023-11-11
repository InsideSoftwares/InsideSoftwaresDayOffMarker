package br.com.insidesoftwares.dayoffmarker.specification.service.day;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Month;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface DayBatchService {

    @Async
    CompletableFuture<Boolean> createDayBatch(final Month month, final int year);
}
