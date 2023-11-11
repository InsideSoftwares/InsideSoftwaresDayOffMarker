package br.com.insidesoftwares.dayoffmarker.service.cache;

import br.com.insidesoftwares.dayoffmarker.specification.service.cache.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
final class CacheServiceBean implements CacheService {

    private final CacheManager cacheManager;

    @Override
    public void clearCache() {
        cacheManager.getCacheNames().forEach(cacheName ->
                {
                    try {
                        Cache cache = cacheManager.getCache(cacheName);
                        Objects.requireNonNull(cache).clear();
                    } catch (Exception e) {
                        log.error("Unable to clear cache: {}", cacheName, e);
                    }
                }
        );
    }
}
