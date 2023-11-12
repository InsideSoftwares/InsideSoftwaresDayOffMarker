package br.com.insidesoftwares.dayoffmarker.specification.service.city;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityRequestDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface CityService {

    InsideSoftwaresResponseDTO<UUID> save(final @Valid CityRequestDTO cityRequestDTO);
    void update(final UUID cityID, final @Valid CityRequestDTO cityRequestDTO);
    void addCityHoliday(final UUID cityID, final @Valid CityHolidayRequestDTO cityHolidayRequestDTO);
    void deleteCityHoliday(final UUID cityID, final @Valid CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO);
}
