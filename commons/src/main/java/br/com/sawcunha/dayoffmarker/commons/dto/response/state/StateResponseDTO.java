package br.com.sawcunha.dayoffmarker.commons.dto.response.state;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StateResponseDTO {

    private Long id;
    private String name;
    private String acronym;
    private Long countryId;
    private String countryName;

}
