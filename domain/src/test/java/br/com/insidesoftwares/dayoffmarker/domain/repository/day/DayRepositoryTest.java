package br.com.insidesoftwares.dayoffmarker.domain.repository.day;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class DayRepositoryTest extends RepositoryTestUtils {

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
    private DayRepository dayRepository;

    @Test
    void shouldReturnNovemberEleventh2023() {
        LocalDate startDate = LocalDate.of(2023, 11, 11);

        Optional<Day> day = dayRepository.findDayById(DAY_ID_11_11_23);

        assertTrue(day.isPresent());
        assertEquals(startDate, day.get().getDate());
    }

    @Test
    void shouldReturnEmptyOptionalWhenProvidedInvalidId() {
        Optional<Day> day = dayRepository.findDayById(DAY_ID_INVALID);

        assertFalse(day.isPresent());
    }

    @Test
    void shouldReturnDateNovemberEleventh2023() {
        LocalDate expectedDate = LocalDate.of(2023, 11, 11);

        LocalDate resultDate = dayRepository.findDateByID(DAY_ID_11_11_23);

        assertEquals(expectedDate, resultDate);
    }

    @Test
    void shouldReturnNullWhenProvidedNonexistentDate() {
        LocalDate resultDate = dayRepository.findDateByID(DAY_ID_INVALID);

        assertNull(resultDate);
    }

    @Test
    void shouldReturnAllDaysOfNovemberWhenProvidingStartDateAndEndDate() {
        LocalDate startDate = LocalDate.of(2023, 11, 1);
        LocalDate endDate = LocalDate.of(2023, 11, 30);

        List<Day> days= dayRepository.findAllByDateSearch(startDate, endDate);

        assertEquals(30, days.size());
    }

//    @Test
//    void shouldReturnFalseWhenCheckingIfCurrentDateExists() {
//        boolean exist = dayRepository.existsDay();
//
//        assertFalse(exist);
//    }

    @Test
    void shouldReturnTrueWhenCheckingIfDateExistsNovemberEleventh2023() {
        LocalDate date = LocalDate.of(2023, 11, 11);

        boolean exist = dayRepository.existsByDate(date);

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenCheckingIfDateExistsNovemberEleventh2024() {
        LocalDate date = LocalDate.of(2024, 11, 11);

        boolean exist = dayRepository.existsByDate(date);

        assertFalse(exist);
    }

    @Test
    void shouldReturnAllDaysWhenProvidingTagId() {
        List<Day> days = dayRepository.findDaysByTagId(TAG_ID_AKLA);

        assertEquals(30, days.size());
    }

    @Test
    void shouldReturnEmptyListWhenProvidingInvalidTagId() {
        List<Day> days = dayRepository.findDaysByTagId(TAG_ID_INVALID);

        assertTrue(days.isEmpty());
    }

    @Test
    void shouldReturnAllDaysWhenProvidingTagCode() {
        List<Day> days = dayRepository.findDaysByTagCode("aKlA");

        assertEquals(30, days.size());
    }

    @Test
    void shouldReturnEmptyListWhenProvidingInvalidTagCode() {
        List<Day> days = dayRepository.findDaysByTagCode("AKLAS");

        assertTrue(days.isEmpty());
    }
}
