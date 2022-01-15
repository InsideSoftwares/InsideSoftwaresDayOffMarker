package br.com.sawcunha.dayoffmarker.configuration.application.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties( prefix="dayoff-marker-cache.config")
public class CacheSettings {

    private List<CacheSettingsModel> caches;

    public List<CacheSettingsModel> getCaches() {
        return caches;
    }

    public void setCaches(List<CacheSettingsModel> caches) {
        this.caches = caches;
    }

    public Map<String,CacheSettingsModel> getCacheConfigAsMap() {
        return Objects.isNull(caches) ? Collections.emptyMap() : caches.stream().
                collect(Collectors.toMap(CacheSettingsModel::getName, c -> c));
    }
}
