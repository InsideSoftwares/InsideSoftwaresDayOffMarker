package br.com.insidesoftwares.dayoffmarker.repository.country;

import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


abstract class CountryRepositoryIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldReturnExistCountryWhenCodeBR01() {
        boolean exist = countryRepository.existsByCode("BR01");

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenCodeBR50() {
        boolean exist = countryRepository.existsByCode("BR50");

        assertFalse(exist);
    }

    @Test
    void shouldReturnCountryWhenNameBrasil() {
        Optional<Country> country = countryRepository.findCountryByName("Brasil");

        assertTrue(country.isPresent());
        assertEquals("Brasil", country.get().getName());
    }

    @Test
    void shouldReturnCountryWhenNameUnidos() {
        Optional<Country> country = countryRepository.findCountryByName("Unidos");

        assertTrue(country.isPresent());
        assertEquals("Estados Unidos", country.get().getName());
    }

    @Test
    void shouldntReturnCountryWhenNameCanada() {
        Optional<Country> country = countryRepository.findCountryByName("Canada");

        assertFalse(country.isPresent());
    }

    @Test
    void shouldReturnExistCountryWhenNameBrasil() {
        boolean exist = countryRepository.existsByName("Brasil");

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenNameCanada() {
        boolean exist = countryRepository.existsByName("Canada");

        assertFalse(exist);
    }

    @Test
    void shouldReturnExistCountryWhenAcronymBRS() {
        boolean exist = countryRepository.existsByAcronym("BRS");

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenAcronymCND() {
        boolean exist = countryRepository.existsByAcronym("CND");

        assertFalse(exist);
    }

    @Test
    void shouldReturnExistCountryWhenNameBrasilAndNotID2() {
        boolean exist = countryRepository.existsByNameAndNotId("Brasil", 2L);

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenNameCanadaAndNotID2() {
        boolean exist = countryRepository.existsByNameAndNotId("Canada", 2L);

        assertFalse(exist);
    }

    @Test
    void shouldReturnExistCountryWhenAcronymBRSAndNotID2() {
        boolean exist = countryRepository.existsByAcronymAndNotId("BRS", 2L);

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenAcronymCNDAndNotID2() {
        boolean exist = countryRepository.existsByAcronymAndNotId("CND", 2L);

        assertFalse(exist);
    }

    @Test
    void shouldReturnExistCountryWhenCodeBR01AndNotID2() {
        boolean exist = countryRepository.existsByCodeAndNotId("BR01", 2L);

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenCodeBR50AndNotID2() {
        boolean exist = countryRepository.existsByCodeAndNotId("BR50", 2L);

        assertFalse(exist);
    }

}
