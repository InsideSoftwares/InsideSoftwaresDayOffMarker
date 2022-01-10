package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.InitRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.InitResponseDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface InitializationService {

    DayOffMarkerResponse<InitResponseDTO> initializationApplication(final @Valid InitRequestDTO initRequestDTO) throws Exception;

}
