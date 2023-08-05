package br.com.insidesoftwares.dayoffmarker.commons.dto.batch;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestParameterDTO {

    private UUID id;
    private TypeParameter typeParameter;
    private TypeValue typeValue;
    private String value;
}
