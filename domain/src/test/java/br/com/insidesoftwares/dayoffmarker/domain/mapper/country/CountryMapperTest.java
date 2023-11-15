package br.com.insidesoftwares.dayoffmarker.domain.mapper.country;

import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.MapperTestUtil;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CountryMapperTest extends MapperTestUtil {

    private final CountryMapper countryMapper = Mappers.getMapper( CountryMapper.class );

    @Test
    void shouldReturnNullWhenNullCountryIsProvided() {
        CountryResponseDTO countryResponseDTO = countryMapper.toDTO(null);

        assertNull(countryResponseDTO);
    }

    @Test
    void shouldReturnNullWhenListWithNullCountriesIsProvided() {
        List<CountryResponseDTO> countryResponseDTOs = countryMapper.toDTOs(null);

        assertNull(countryResponseDTOs);
    }

    @Test
    void shouldReturnCountryResponseDTOWhenValidCountryIsProvided() {
        UUID id = UUID.randomUUID();
        CountryResponseDTO countryResponseDTOExpected = createCountryResponseDTO(id);
        CountryResponseDTO countryResponseDTO = countryMapper.toDTO(createCountry(id));

        assertEquals(countryResponseDTOExpected, countryResponseDTO);
    }

    @Test
    void shouldReturnListCountryResponseDTOWhenValidListCountryIsProvided() {
        UUID id = UUID.randomUUID();
        List<CountryResponseDTO> countryResponseDTOsExpected = createCountryResponseDTOs(id);
        List<CountryResponseDTO> countryResponseDTOs = countryMapper.toDTOs(createCountrys(id));

        assertEquals(countryResponseDTOsExpected.get(0), countryResponseDTOs.get(0));
    }
}
