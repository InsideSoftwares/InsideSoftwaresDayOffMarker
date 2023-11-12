package br.com.insidesoftwares.dayoffmarker.specification.service.holiday;

import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface FixedHolidayBatchService {

    @Async
    CompletableFuture<Boolean> createHolidayBatch(final FixedHoliday fixedHoliday);

}
