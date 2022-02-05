package br.com.sawcunha.dayoffmarker.commons.dto.request.link;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LinkDayRequestDTO extends DayOffMarkerDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "DOMV-003")
	private Set<LocalDate> daysID;
}
