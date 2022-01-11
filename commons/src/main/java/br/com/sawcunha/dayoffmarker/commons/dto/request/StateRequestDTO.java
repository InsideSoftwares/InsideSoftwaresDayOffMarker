package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class StateRequestDTO {

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    private String name;

    @NotNull(message = "DOMV-003")
    @NotEmpty(message = "DOMV-001")
    @Size(min = 2, max = 10, message = "DOMV-006")
    private String acronym;

    @NotNull(message = "DOMV-003")
    private Long countryId;

}
