package br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort;

import br.com.insidesoftwares.commons.sort.PropertiesOrder;
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
