package br.com.insidesoftwares.dayoffmarker.job.scheduled.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConditionalOnProperty(
	prefix="spring.cache", name = "enable",
	havingValue = "true",
	matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class CacheJob {

	private final CacheManager cacheManager;

	@Scheduled(
		cron = "${application.scheduling.cache.clean:0 0 */12 * * *}"
	)
	public void clearCacheScheduled(){
		log.info("Starting clear cache.");
		try{

			cacheManager.getCacheNames().forEach(cacheName ->
				{
					try{
						Cache cache = cacheManager.getCache(cacheName);
						Objects.requireNonNull(cache).clear();
					}catch (Exception e){
						log.error("Não foi possível limpar o cache: {}", cacheName);
						log.error("Não foi possível limpar o cach", e);
					}
				}
			);
		}catch (Exception e){
			log.info("An error occurred while trying to clean up the caches",e);
		} finally {
			log.info("Finalizing clear cache.");
		}
	}

}
