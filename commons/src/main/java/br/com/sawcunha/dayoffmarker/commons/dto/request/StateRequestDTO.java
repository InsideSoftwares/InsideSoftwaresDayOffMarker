package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class StateRequestDTO {

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    private String name;

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    @Size(min = 2, max = 10, message = "DCYV-006")
    private String acronym;

    @NotNull(message = "DCYV-003")
    private Long countryId;

}
