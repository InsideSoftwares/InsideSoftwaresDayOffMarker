package br.com.sawcunha.dayoffmarker.commons.dto.response.initialization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String adminKey;
    private String viewKey;
    private String requestKey;

}
