package br.com.sawcunha.dayoffmarker.commons.enums.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderFixedHoliday implements PropertiesOrder {

    ID("id"),
    NAME("name"),
    DAY("day"),
    MONTH("month"),
    COUNTRY_NAME("country.name");

    private final String properties;


    @Override
    public String properties() {
        return this.getProperties();
    }
}
