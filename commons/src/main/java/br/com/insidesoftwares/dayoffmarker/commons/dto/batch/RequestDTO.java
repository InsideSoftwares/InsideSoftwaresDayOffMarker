package br.com.insidesoftwares.dayoffmarker.commons.dto.batch;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
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
    private StatusRequest statusRequest;
    private TypeRequest typeRequest;
    private Set<RequestParameterDTO> requestParameter;

}
