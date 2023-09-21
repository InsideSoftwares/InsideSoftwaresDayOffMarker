package br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort;

import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum eOrderDay implements PropertiesOrder {
    ID("id"),
    DATE("date"),
	IS_WEEKEND("isWeekend"),
	IS_HOLIDAY("isHoliday"),
	DAY_OF_YEAR("dayOfYear"),
    COUNTRY_NAME("country.name"),
    HOLIDAY_NAME("holiday.name");

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
