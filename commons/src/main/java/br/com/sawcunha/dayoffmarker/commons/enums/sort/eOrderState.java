package br.com.sawcunha.dayoffmarker.commons.enums.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderState implements PropertiesOrder {

    ID("id"),
    NAME("name"),
    ACRONYM("acronym");

    private final String properties;

    @Override
    public String properties() {
        return this.getProperties();
    }
}
