package br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort;

import br.com.insidesoftwares.commons.sort.PropertiesOrder;
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

    @Override
    public String value(String name) {
        try{
            return eOrderCity.valueOf(name).getProperties();
        }catch (Exception exception) {
            return this.getProperties();
        }
    }
}
