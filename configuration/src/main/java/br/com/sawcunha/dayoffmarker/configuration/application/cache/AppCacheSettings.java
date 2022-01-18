package br.com.sawcunha.dayoffmarker.configuration.application.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties( prefix="dayoff-marker-cache.dayoff-caches")
public class AppCacheSettings {

    private Map<String,String> caches;

    public Map<String, String> getCaches() {
        return caches;
    }

    public void setCaches(Map<String, String> caches) {
        this.caches = caches;
    }
}
