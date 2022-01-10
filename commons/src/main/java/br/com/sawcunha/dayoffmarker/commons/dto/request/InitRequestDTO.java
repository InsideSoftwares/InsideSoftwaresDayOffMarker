package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitRequestDTO {

    private UUID adminKey;
    private UUID viewKey;

    @NotNull(message = "DCYV-003")
    @Min(value = 1, message = "DCYV-002")
    @Max(value = 15, message = "DCYV-004")
    private Integer numberBackYears;
    @NotNull(message = "DCYV-003")
    @Min(value = 1, message = "DCYV-002")
    @Max(value = 25, message = "DCYV-004")
    private Integer numberForwardYears;

    private String countryDefault;

}
