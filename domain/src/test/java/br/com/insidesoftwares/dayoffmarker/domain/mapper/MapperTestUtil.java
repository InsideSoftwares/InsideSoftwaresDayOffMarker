package br.com.insidesoftwares.dayoffmarker.domain.mapper;

import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;

import java.util.List;
import java.util.UUID;

public abstract class MapperTestUtil {


    protected Country createCountry(final UUID id) {
        return Country.builder()
                .id(id)
                .acronym("TEST")
                .name("Country")
                .code("COTS")
                .build();
    }

    protected CountryResponseDTO createCountryResponseDTO(final UUID id) {
        return CountryResponseDTO.builder()
                .id(id)
                .acronym("TEST")
                .name("Country")
                .code("COTS")
                .build();
    }

    protected List<Country> createCountrys(final UUID id) {
        return List.of(createCountry(id));
    }

    protected List<CountryResponseDTO> createCountryResponseDTOs(final UUID id) {
        return List.of(createCountryResponseDTO(id));
    }

    protected Tag createTag(final UUID id) {
        return Tag.builder()
                .id(id)
                .code("TAG")
                .description("TAG")
                .build();
    }

    protected TagResponseDTO createTagResponseDTO(final UUID id) {
        return TagResponseDTO.builder()
                .id(id)
                .code("TAG")
                .description("TAG")
                .build();
    }

    protected List<Tag> createTags(final UUID id) {
        return List.of(createTag(id));
    }

    protected List<TagResponseDTO> createTagResponseDTOs(final UUID id) {
        return List.of(createTagResponseDTO(id));
    }
}
