package br.com.insidesoftwares.dayoffmarker.domain.entity.city;

import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "DOM_CITY_HOLIDAY")
public class CityHoliday {

	@EmbeddedId
	private CityHolidayPK id;

	@Column(name = "HOLIDAY")
	private boolean cityHoliday;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOLIDAY_ID", insertable=false, updatable=false)
	private Holiday holiday;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID", insertable=false, updatable=false)
	private City city;

	public CityHoliday(CityHolidayPK id, boolean cityHoliday) {
		this.id = id;
		this.cityHoliday = cityHoliday;
	}
}
