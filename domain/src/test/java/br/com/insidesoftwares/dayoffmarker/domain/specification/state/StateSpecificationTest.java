package br.com.insidesoftwares.dayoffmarker.domain.specification.state;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static br.com.insidesoftwares.dayoffmarker.domain.specification.state.StateSpecification.findByStateHolidayByStateAndStateHolidayAndDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class StateSpecificationTest extends RepositoryTestUtils {

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
    private StateRepository stateRepository;

    @Test
    void shouldReturnTrueWhenSearchingForStateThatIsHoliday() {
        LocalDate dateSearch = LocalDate.of(2023, 11, 20);

        Specification<State> stateSpecification = findByStateHolidayByStateAndStateHolidayAndDate(STATE_SP_ID, true, dateSearch);

        boolean exist = stateRepository.exists(stateSpecification);

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForStateThatIsNotHoliday() {
        LocalDate dateSearch = LocalDate.of(2023, 11, 20);

        Specification<State> stateSpecification = findByStateHolidayByStateAndStateHolidayAndDate(STATE_MG_ID, true, dateSearch);

        boolean exist = stateRepository.exists(stateSpecification);

        assertFalse(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForInvalidDate() {
        LocalDate dateSearch = LocalDate.of(2022, 11, 20);

        Specification<State> stateSpecification = findByStateHolidayByStateAndStateHolidayAndDate(STATE_MG_ID, true, dateSearch);

        boolean exist = stateRepository.exists(stateSpecification);

        assertFalse(exist);
    }
}
