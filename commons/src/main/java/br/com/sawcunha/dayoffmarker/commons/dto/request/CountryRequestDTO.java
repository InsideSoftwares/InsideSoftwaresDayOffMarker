package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class CountryRequestDTO {

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    private String name;

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    @Size(min = 2, max = 10, message = "DCYV-006")
    private String acronym;

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    @Size(min = 3, max = 15, message = "DCYV-006")
    private String code;

}
