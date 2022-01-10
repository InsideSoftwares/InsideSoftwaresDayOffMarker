package br.com.sawcunha.dayoffmarker.commons.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {

    @NotNull(message = "DCYV-003")
    private UUID key;

    @NotNull(message = "DCYV-003")
    @NotEmpty(message = "DCYV-001")
    private String name;

    @NotNull(message = "DCYV-003")
    @Email(message = "DCYV-005")
    private String mail;

}