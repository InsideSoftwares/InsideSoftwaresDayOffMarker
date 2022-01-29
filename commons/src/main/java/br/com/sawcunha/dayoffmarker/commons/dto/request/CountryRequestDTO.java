package br.com.sawcunha.dayoffmarker.commons.dto.request;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class CountryRequestDTO extends DayOffMarkerDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String name;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    @Size(min = 2, max = 10, message = "DOMV-006")
    private String acronym;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    @Size(min = 3, max = 15, message = "DOMV-006")
    private String code;

}
