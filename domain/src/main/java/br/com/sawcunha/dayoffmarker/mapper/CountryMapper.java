package br.com.sawcunha.dayoffmarker.mapper;

import br.com.sawcunha.dayoffmarker.commons.dto.response.country.CountryResponseDTO;
import br.com.sawcunha.dayoffmarker.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryResponseDTO toDTO(Country country);
    List<CountryResponseDTO> toDTOs(List<Country> countries);

}
