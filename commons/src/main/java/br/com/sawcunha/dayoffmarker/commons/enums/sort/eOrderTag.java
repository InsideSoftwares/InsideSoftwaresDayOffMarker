package br.com.sawcunha.dayoffmarker.commons.enums.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderTag implements PropertiesOrder {

    ID("id"),
    DESCRIPTION("description"),
    CODE("code");

    private final String properties;

    @Override
    public String properties() {
        return this.getProperties();
    }
}
