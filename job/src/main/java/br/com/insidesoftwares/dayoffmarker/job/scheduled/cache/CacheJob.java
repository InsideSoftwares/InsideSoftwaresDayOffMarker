package br.com.insidesoftwares.dayoffmarker.job.scheduled.cache;

import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Objects;

@Component
@ConditionalOnProperty(
	prefix="spring.cache", name = "enable",
	havingValue = "true",
	matchIfMissing = true)
@RequiredArgsConstructor
public class CacheJob {

	private final LogService<CacheJob> logService;
	private final CacheManager cacheManager;

	@PostConstruct
	public void init(){
		logService.init(CacheJob.class);
		logService.logInfor("Init CacheJob");
	}

	@Scheduled(
		cron = "${application.scheduling.cache.clean:0 0 */12 * * *}"
	)
	public void clearCacheScheduled(){
		logService.logInfor("Starting clear cache.");
		try{

			cacheManager.getCacheNames().forEach(cacheName ->
				{
					try{
						Cache cache = cacheManager.getCache(cacheName);
						Objects.requireNonNull(cache).clear();
					}catch (Exception e){
						logService.logErrorByArgs("Não foi possível limpar o cache: {}", cacheName);
						logService.logError("Não foi possível limpar o cach", e);
					}
				}
			);
		}catch (Exception e){
			logService.logInfor("An error occurred while trying to clean up the caches",e);
		} finally {
			logService.logInfor("Finalizing clear cache.");
		}
	}

}
