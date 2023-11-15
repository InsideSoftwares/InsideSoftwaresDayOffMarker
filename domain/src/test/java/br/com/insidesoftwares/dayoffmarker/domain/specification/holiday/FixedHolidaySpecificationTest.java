package br.com.insidesoftwares.dayoffmarker.domain.specification.holiday;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class FixedHolidaySpecificationTest extends RepositoryTestUtils {

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
    void shouldReturnEmptyListWhenSearchingForDayOfTheDeadHoliday() {

        Specification<Day> daySpecification = FixedHolidaySpecification.findDayWithoutHolidaysByDayAndMonthAndYearAndFixedHolidayIdOrNotHoliday(2, 11, FIXED_HOLIDAY_2_11_ID);
        List<Day> days = dayRepository.findAll(daySpecification);
        assertTrue(days.isEmpty());
    }

    @Test
    void shouldReturnListNovemberFirstWhenSearchingForDayOfTheDeadHoliday() {
        LocalDate expectedDate = LocalDate.of(2023,11,1);

        Specification<Day> daySpecification = FixedHolidaySpecification.findDayWithoutHolidaysByDayAndMonthAndYearAndFixedHolidayIdOrNotHoliday(1, 11, FIXED_HOLIDAY_2_11_ID);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        assertTrue(days.stream().anyMatch(day -> day.getDate().isEqual(expectedDate)));
    }
}
