package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CityService {

    InsideSoftwaresResponseDTO<Long> save(final @Valid CityRequestDTO cityRequestDTO);

    void update(final Long cityID, final @Valid CityRequestDTO cityRequestDTO);

    void addCityHoliday(final Long cityID, final @Valid CityHolidayRequestDTO cityHolidayRequestDTO);

    void deleteCityHoliday(final Long cityID, final @Valid CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO);
}
