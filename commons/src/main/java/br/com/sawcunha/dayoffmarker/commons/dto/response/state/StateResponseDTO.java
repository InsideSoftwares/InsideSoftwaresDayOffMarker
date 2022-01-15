package br.com.sawcunha.dayoffmarker.commons.dto.response.state;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class StateResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String acronym;
    private Long countryId;
    private String countryName;

}
