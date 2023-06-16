package br.com.insidesoftwares.dayoffmarker.entity.state;

import br.com.insidesoftwares.dayoffmarker.entity.Holiday;
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
@Table(name = "DOM_STATE_HOLIDAY")
public class StateHoliday {

	@EmbeddedId
	private StateHolidayPK id;

	@Column(name = "HOLIDAY")
	private boolean stateHoliday;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOLIDAY_ID", insertable=false, updatable=false)
	private Holiday holiday;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID", insertable=false, updatable=false)
	private State state;

	public StateHoliday(StateHolidayPK id, boolean stateHoliday) {
		this.id = id;
		this.stateHoliday = stateHoliday;
	}
}
