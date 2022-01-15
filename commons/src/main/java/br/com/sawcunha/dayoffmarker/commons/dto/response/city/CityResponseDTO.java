package br.com.sawcunha.dayoffmarker.commons.dto.response.city;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class CityResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String acronym;
    private String code;
    private Long stateID;
    private String stateName;
    private String stateAcronym;

}
