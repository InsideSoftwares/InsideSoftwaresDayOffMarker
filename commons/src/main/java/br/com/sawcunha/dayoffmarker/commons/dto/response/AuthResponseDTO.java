package br.com.sawcunha.dayoffmarker.commons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthResponseDTO {

    private String token;
    private LocalDateTime request;
    private Integer expiretion;
    private LocalDateTime expiretionTime;

}
