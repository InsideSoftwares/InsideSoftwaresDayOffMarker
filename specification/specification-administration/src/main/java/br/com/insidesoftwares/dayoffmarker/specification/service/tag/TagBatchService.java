package br.com.insidesoftwares.dayoffmarker.specification.service.tag;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface TagBatchService {

    @Async
    CompletableFuture<Boolean> linkDayTag(final UUID tagId, final Specification<Day> daySpecification);
    @Async
    CompletableFuture<Boolean> unlinkDayTag(final UUID tagId, final Specification<Day> daySpecification);
}
