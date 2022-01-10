package br.com.sawcunha.dayoffmarker.commons.dto.batch;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestParameterDTO {

    private Long id;
    private eTypeParameter typeParameter;
    private eTypeValue typeValue;
    private String value;
}
