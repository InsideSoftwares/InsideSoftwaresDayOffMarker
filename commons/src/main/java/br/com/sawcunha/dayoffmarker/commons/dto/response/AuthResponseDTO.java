package br.com.sawcunha.dayoffmarker.commons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private LocalDateTime request;
    private Integer expiretion;
    private LocalDateTime expiretionTime;

}
