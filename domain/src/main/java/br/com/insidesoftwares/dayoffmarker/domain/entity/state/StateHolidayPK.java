package br.com.insidesoftwares.dayoffmarker.domain.entity.state;

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
public class StateHolidayPK {
	@Column(name = "STATE_ID")
	private Long stateId;
	@Column(name = "HOLIDAY_ID")
	private Long holidayId;
}
