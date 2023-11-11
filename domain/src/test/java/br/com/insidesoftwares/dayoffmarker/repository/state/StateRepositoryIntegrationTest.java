package br.com.insidesoftwares.dayoffmarker.repository.state;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils.createPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/insert_country_state_city.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_country_state_city.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class StateRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    private static final UUID COUNTRY_ID = UUID.fromString("2596e281-4dc1-40bf-afb5-08b245de54a6");
    private static final UUID STATE_RIO_JANEIRO_ID = UUID.fromString("f7485292-7c4d-48c8-b46e-00efda156c45");
    private static final UUID STATE_MINAS_GERAIS_ID = UUID.fromString("938b4593-6eb5-4a3c-a504-eacaca04d893");
    private static final UUID STATE_ID_INVALID = UUID.randomUUID();
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldReturnEstadoRioDeJaneiroWhenInformedIDCorrect() {

        Optional<State> state = stateRepository.findStateById(STATE_RIO_JANEIRO_ID);

        assertTrue(state.isPresent());
        assertEquals(STATE_RIO_JANEIRO_ID, state.get().getId());
        assertEquals("RJ01", state.get().getCode());
    }

    @Test
    void shouldntReturnStatusWhenInformedIDInvalid() {

        Optional<State> state = stateRepository.findStateById(STATE_ID_INVALID);

        assertFalse(state.isPresent());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByID() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.ASC, eOrderCity.ID.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(STATE_MINAS_GERAIS_ID, statePage.getContent().get(0).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByID() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.DESC, eOrderCity.ID.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(3L, statePage.getContent().get(0).getId());

        assertEquals("RJ01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.ASC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(1L, statePage.getContent().get(0).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(2L, statePage.getContent().get(0).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByCodeAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 5, Sort.Direction.ASC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(1, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(3, statePage.getContent().size());

        assertEquals(1L, statePage.getContent().get(0).getId());
        assertEquals(3L, statePage.getContent().get(1).getId());
        assertEquals(2L, statePage.getContent().get(2).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
        assertEquals("RJ01", statePage.getContent().get(1).getCode());
        assertEquals("SP01", statePage.getContent().get(2).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByCodeAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 5, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(1, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(3, statePage.getContent().size());

        assertEquals(2L, statePage.getContent().get(0).getId());
        assertEquals(3L, statePage.getContent().get(1).getId());
        assertEquals(1L, statePage.getContent().get(2).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
        assertEquals("RJ01", statePage.getContent().get(1).getCode());
        assertEquals("MG01", statePage.getContent().get(2).getCode());
    }

}
