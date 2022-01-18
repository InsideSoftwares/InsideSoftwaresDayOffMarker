package br.com.sawcunha.dayoffmarker.commons.dto.batch;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestDTO {

    private UUID id;
    private String requesting;
    private LocalDate startDate;
    private LocalDate endDate;
    private eStatusRequest statusRequest;
    private eTypeRequest typeRequest;
    private Set<RequestParameterDTO> requestParameter;

}
