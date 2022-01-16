package br.com.sawcunha.dayoffmarker.scheduled.cache;

import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
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
		fixedRateString = "${application.scheduling.cache.clear.fixedRateInMilliseconds}",
		initialDelayString = "${application.scheduling.cache.initialDelayInMilliseconds}"
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
