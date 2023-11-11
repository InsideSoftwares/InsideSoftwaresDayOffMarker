package br.com.insidesoftwares.dayoffmarker.scheduled.cache;

import br.com.insidesoftwares.dayoffmarker.specification.service.cache.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(
        prefix = "spring.cache", name = "enable",
        havingValue = "true",
        matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class CacheScheduledComponent {

    private final CacheService cacheService;

    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public void clearCacheScheduled() {
        log.info("Starting clear cache.");
        try {
            cacheService.clearCache();
        } catch (Exception e) {
            log.info("An error occurred while trying to clean up the caches", e);
        } finally {
            log.info("Finalizing clear cache.");
        }
    }

}
