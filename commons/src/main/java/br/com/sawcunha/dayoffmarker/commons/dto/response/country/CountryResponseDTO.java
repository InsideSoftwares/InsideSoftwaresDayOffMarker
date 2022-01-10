package br.com.sawcunha.dayoffmarker.commons.dto.response.country;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CountryResponseDTO {

    private Long id;
    private String name;
    private String acronym;
    private String code;

}
