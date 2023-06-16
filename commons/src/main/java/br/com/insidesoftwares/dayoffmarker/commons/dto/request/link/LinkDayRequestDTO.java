package br.com.insidesoftwares.dayoffmarker.commons.dto.request.link;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LinkDayRequestDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "DOMV-003")
	private Set<LocalDate> daysID;
}
