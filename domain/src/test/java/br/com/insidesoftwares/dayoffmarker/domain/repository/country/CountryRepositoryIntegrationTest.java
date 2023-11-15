package br.com.insidesoftwares.dayoffmarker.domain.repository.country;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class CountryRepositoryIntegrationTest extends RepositoryTestUtils {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

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
        Optional<Country> country = countryRepository.findCountryByName("BrA");

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
    void shouldReturnExistCountryWhenNameBrasilAndNotID() {
        boolean exist = countryRepository.existsByNameAndNotId("Brasil", COUNTRY_EUA_ID);

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenNameCanadaAndNotID() {
        boolean exist = countryRepository.existsByNameAndNotId("Canada", COUNTRY_BRS_ID);

        assertFalse(exist);
    }

    @Test
    void shouldReturnExistCountryWhenAcronymBRSAndNotID() {
        boolean exist = countryRepository.existsByAcronymAndNotId("BRS", COUNTRY_EUA_ID);

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenAcronymCNDAndNotID() {
        boolean exist = countryRepository.existsByAcronymAndNotId("CND", COUNTRY_BRS_ID);

        assertFalse(exist);
    }

    @Test
    void shouldReturnExistCountryWhenCodeBR01AndNotID() {
        boolean exist = countryRepository.existsByCodeAndNotId("BR01", COUNTRY_EUA_ID);

        assertTrue(exist);
    }

    @Test
    void shouldntReturnExistCountryWhenCodeBR50AndNotID() {
        boolean exist = countryRepository.existsByCodeAndNotId("BR50", COUNTRY_BRS_ID);

        assertFalse(exist);
    }

}
