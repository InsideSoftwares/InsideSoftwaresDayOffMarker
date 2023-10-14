package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CountryService {

    InsideSoftwaresResponseDTO<Long> save(final @Valid CountryRequestDTO countryRequestDTO);

    void update(final Long countryID, final @Valid CountryRequestDTO countryRequestDTO);
}
