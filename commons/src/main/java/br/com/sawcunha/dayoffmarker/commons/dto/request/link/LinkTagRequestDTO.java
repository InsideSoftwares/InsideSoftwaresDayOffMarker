package br.com.sawcunha.dayoffmarker.commons.dto.request.link;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkTagRequestDTO extends DayOffMarkerDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "DOMV-003")
	private Set<Long> tagsID;
}
