package br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort;

import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderHoliday implements PropertiesOrder {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    HOLIDAY_TYPE("holidayType"),
    DAY("day.date");

    private final String properties;

    @Override
    public String properties() {
        return this.getProperties();
    }

    @Override
    public String value(String name) {
        try {
            return eOrderCity.valueOf(name).getProperties();
        } catch (Exception exception) {
            return this.getProperties();
        }
    }
}
