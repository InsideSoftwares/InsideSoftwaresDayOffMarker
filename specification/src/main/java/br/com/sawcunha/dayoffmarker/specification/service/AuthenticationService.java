package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.AuthRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.AuthResponseDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface AuthenticationService {

    DayOffMarkerResponse<AuthResponseDTO> login(final @Valid AuthRequestDTO authDTO) throws Exception;
}
