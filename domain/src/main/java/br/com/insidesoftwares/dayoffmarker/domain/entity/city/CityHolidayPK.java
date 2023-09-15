package br.com.insidesoftwares.dayoffmarker.domain.entity.city;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CityHolidayPK {
	@Column(name = "CITY_ID")
	private Long cityId;
	@Column(name = "HOLIDAY_ID")
	private Long holidayId;
}
