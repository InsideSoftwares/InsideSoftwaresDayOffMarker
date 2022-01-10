package br.com.sawcunha.dayoffmarker.commons.enums.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderCity implements PropertiesOrder {

    ID("id"),
    NAME("name"),
    ACRONYM("acronym"),
    CODE("code"),
    STATE_NAME("state.name"),
    STATE_ACRONYM("state.acronym");

    private final String properties;

    @Override
    public String properties() {
        return this.getProperties();
    }
}
