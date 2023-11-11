package br.com.insidesoftwares.dayoffmarker.specification.service.country;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface CountryService {

    InsideSoftwaresResponseDTO<UUID> save(final @Valid CountryRequestDTO countryRequestDTO);

    void update(final UUID countryID, final @Valid CountryRequestDTO countryRequestDTO);
}
