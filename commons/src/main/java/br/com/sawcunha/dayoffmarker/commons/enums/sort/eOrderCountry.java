package br.com.sawcunha.dayoffmarker.commons.enums.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderCountry implements PropertiesOrder {

    ID("id"),
    NAME("name"),
    ACRONYM("acronym"),
    CODE("code");

    private final String properties;

    @Override
    public String properties() {
        return this.getProperties();
    }
}
