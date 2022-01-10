package br.com.sawcunha.dayoffmarker.commons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitResponseDTO {

    private String adminKey;
    private String viewKey;
    private String requestKey;

}
