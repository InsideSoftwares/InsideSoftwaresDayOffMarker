package br.com.sawcunha.dayoffmarker.commons.enums.sort;

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
}
