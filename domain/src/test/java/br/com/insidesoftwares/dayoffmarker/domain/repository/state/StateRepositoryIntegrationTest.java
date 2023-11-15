package br.com.insidesoftwares.dayoffmarker.domain.repository.state;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class StateRepositoryIntegrationTest extends RepositoryTestUtils {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    private static final UUID STATE_ID_INVALID = UUID.randomUUID();
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldReturnEstadoRioDeJaneiroWhenInformedIDCorrect() {

        Optional<State> state = stateRepository.findStateById(STATE_RJ_ID);

        assertTrue(state.isPresent());
        assertEquals(STATE_RJ_ID, state.get().getId());
        assertEquals("RJ01", state.get().getCode());
    }

    @Test
    void shouldntReturnStatusWhenInformedIDInvalid() {

        Optional<State> state = stateRepository.findStateById(STATE_ID_INVALID);

        assertFalse(state.isPresent());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByID() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.ASC, eOrderCity.ID.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(STATE_SP_ID, statePage.getContent().get(0).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByID() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.DESC, eOrderCity.ID.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(STATE_RJ_ID, statePage.getContent().get(0).getId());

        assertEquals("RJ01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.ASC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(STATE_MG_ID, statePage.getContent().get(0).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(STATE_SP_ID, statePage.getContent().get(0).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByCodeAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 5, Sort.Direction.ASC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(1, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(3, statePage.getContent().size());

        assertEquals(STATE_MG_ID, statePage.getContent().get(0).getId());
        assertEquals(STATE_RJ_ID, statePage.getContent().get(1).getId());
        assertEquals(STATE_SP_ID, statePage.getContent().get(2).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
        assertEquals("RJ01", statePage.getContent().get(1).getCode());
        assertEquals("SP01", statePage.getContent().get(2).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByCodeAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 5, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(1, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(3, statePage.getContent().size());

        assertEquals(STATE_SP_ID, statePage.getContent().get(0).getId());
        assertEquals(STATE_RJ_ID, statePage.getContent().get(1).getId());
        assertEquals(STATE_MG_ID, statePage.getContent().get(2).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
        assertEquals("RJ01", statePage.getContent().get(1).getCode());
        assertEquals("MG01", statePage.getContent().get(2).getCode());
    }

    @Test
    void shouldReturnTrueWhenSearchingForStateAndCountryIdAndExistingAbbreviation() {
        boolean exist = stateRepository.existsByNameAndCountryIdAndAcronym("Minas GERAIS", COUNTRY_BRS_ID, "MG");

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForStateAndCountryIdAndNonexistentAbbreviation() {
        boolean exist = stateRepository.existsByNameAndCountryIdAndAcronym("Minas GERAL", COUNTRY_BRS_ID, "MG");

        assertFalse(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForExistingStateAndNonexistentCountryIdAndExistingAbbreviation() {
        boolean exist = stateRepository.existsByNameAndCountryIdAndAcronym("Minas GERAIS", COUNTRY_ID_INVALID, "MG");

        assertFalse(exist);
    }

    @Test
    void shouldReturnTrueWhenSearchingForCountryIdAndExistingAbbreviation() {
        boolean exist = stateRepository.existsByCountryIdAndAcronym(COUNTRY_BRS_ID, "MG");

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForExistingCountryIdAndNonexistentAbbreviation() {
        boolean exist = stateRepository.existsByCountryIdAndAcronym(COUNTRY_BRS_ID, "MF");

        assertFalse(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForNonexistentCountryIdAndExistingAbbreviation() {
        boolean exist = stateRepository.existsByCountryIdAndAcronym(COUNTRY_ID_INVALID, "MG");

        assertFalse(exist);
    }

    @Test
    void shouldReturnTrueWhenSearchingForNameAndCountryIdAndAbbreviationAndDifferentStateId() {
        boolean exist = stateRepository.existsByNameAndCountryIdAndAcronymAndNotId("Minas GERAIS", COUNTRY_BRS_ID, "MG", STATE_SP_ID);

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForNameAndCountryIdAndAbbreviationAndSameStateId() {
        boolean exist = stateRepository.existsByNameAndCountryIdAndAcronymAndNotId("Minas GERAIS", COUNTRY_BRS_ID, "MG", STATE_MG_ID);

        assertFalse(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForNameAndNonexistentCountryIdAndAbbreviationAndDifferentStateId() {
        boolean exist = stateRepository.existsByNameAndCountryIdAndAcronymAndNotId("Minas GERAIS", COUNTRY_EUA_ID, "MG", STATE_RJ_ID);

        assertFalse(exist);
    }

    @Test
    void shouldReturnTrueWhenSearchingForCountryIdAndAbbreviationAndDifferentStateId() {
        boolean exist = stateRepository.existsByCountryIdAndAcronymAndNotId(COUNTRY_BRS_ID, "MG", STATE_SP_ID);

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForCountryIdAndAbbreviationAndSameStateId() {
        boolean exist = stateRepository.existsByCountryIdAndAcronymAndNotId(COUNTRY_BRS_ID, "MG", STATE_MG_ID);

        assertFalse(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForNonexistentCountryIdAndAbbreviationAndDifferentStateId() {
        boolean exist = stateRepository.existsByCountryIdAndAcronymAndNotId(COUNTRY_EUA_ID, "MG", STATE_RJ_ID);

        assertFalse(exist);
    }

}
