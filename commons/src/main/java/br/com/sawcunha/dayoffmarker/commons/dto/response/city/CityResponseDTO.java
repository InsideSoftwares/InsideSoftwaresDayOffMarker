package br.com.sawcunha.dayoffmarker.commons.dto.response.city;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CityResponseDTO {

    private Long id;
    private String name;
    private String acronym;
    private String code;
    private Long stateID;
    private String stateName;
    private String stateAcronym;

}
