package br.com.sawcunha.dayoffmarker.configuration.application.cache;

public class CacheSettingsModel {
    private String name;
    private String timeToLiveSeconds;

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public final void setTimeToLiveSeconds(String timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }
}
