package br.com.insidesoftwares.dayoffmarker.service.tag;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTag;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTagPK;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.tag.DayTagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.tag.TagBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagBatchServiceBean implements TagBatchService {

    private final DayRepository dayRepository;
    private final DayTagRepository dayTagRepository;

    @Async
    @Override
    public CompletableFuture<Boolean> linkDayTag(final UUID tagId, final Specification<Day> daySpecification) {
        boolean success = true;

        try {
            List<Day> days = findAllIdsDays(daySpecification);

            List<DayTag> dayTags = days.parallelStream()
                    .filter(day -> !containsTag(day, tagId))
                    .map(day -> createDayTag(day.getId(), tagId)).toList();

            if(!dayTags.isEmpty()) {
                dayTagRepository.saveAllAndFlush(dayTags);
                log.info("linkDayTag - Success");
            }

        } catch (Exception exception) {
            log.error("linkDayTag - Error", exception);

            success = false;
        }

        return CompletableFuture.completedFuture(success);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> unlinkDayTag(final UUID tagId, final Specification<Day> daySpecification) {
        boolean success = true;

        try {
            List<Day> days = findAllIdsDays(daySpecification);

            List<DayTag> dayTags = days.parallelStream()
                    .filter(day -> containsTag(day, tagId))
                    .map(day -> createDayTag(day.getId(), tagId)).toList();

            if(!dayTags.isEmpty()) {
                dayTagRepository.deleteAllInBatch(dayTags);
                log.info("unlinkDayTag - Success");
            }

        } catch (Exception exception) {
            log.error("unlinkDayTag - Error", exception);

            success = false;
        }

        return CompletableFuture.completedFuture(success);
    }

    private List<Day> findAllIdsDays(final Specification<Day> daySpecification) {
        return dayRepository.findAll(daySpecification);
    }

    private boolean containsTag(final Day day, final UUID tagId) {
        return day.getTags().stream().anyMatch(tag -> tag.getId().equals(tagId));
    }

    private DayTag createDayTag(final UUID dayID, final UUID tagID) {
        DayTagPK dayTagPK = DayTagPK.builder()
                .dayID(dayID)
                .tagID(tagID)
                .build();

        return DayTag.builder()
                .id(dayTagPK)
                .build();
    }
}
