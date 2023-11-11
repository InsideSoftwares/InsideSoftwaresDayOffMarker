package br.com.insidesoftwares.dayoffmarker.domain.mapper.country;

import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryResponseDTO toDTO(Country country);

    List<CountryResponseDTO> toDTOs(List<Country> countries);

}
