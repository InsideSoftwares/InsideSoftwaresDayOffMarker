package br.com.sawcunha.dayoffmarker.commons.dto.request;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitRequestDTO extends DayOffMarkerDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID adminKey;
    private UUID viewKey;

    @NotNull(message = "DOMV-003")
    @Min(value = 1, message = "DOMV-002")
    @Max(value = 15, message = "DOMV-004")
    private Integer numberBackYears;
    @NotNull(message = "DOMV-003")
    @Min(value = 1, message = "DOMV-002")
    @Max(value = 25, message = "DOMV-004")
    private Integer numberForwardYears;

    private String countryDefault;

}
