package br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort;

import br.com.insidesoftwares.commons.sort.PropertiesOrder;
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
