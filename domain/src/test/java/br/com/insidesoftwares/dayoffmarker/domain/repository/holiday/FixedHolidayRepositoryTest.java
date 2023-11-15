package br.com.insidesoftwares.dayoffmarker.domain.repository.holiday;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class FixedHolidayRepositoryTest extends RepositoryTestUtils {

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
    private FixedHolidayRepository fixedHolidayRepository;

    @Test
    void shouldReturnTrueWhenSearchingForExistingDayAndMonth() {
        boolean exist = fixedHolidayRepository.existsByDayAndMonth(1, 1);

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForNonexistentDayAndMonth() {
        boolean exist = fixedHolidayRepository.existsByDayAndMonth(1, 2);

        assertFalse(exist);
    }

    @Test
    void shouldReturnTrueWhenSearchingForExistingDayAndMonthAndExistingFixedHolidayId() {
        boolean exist = fixedHolidayRepository.existsByDayAndMonthAndNotId(1, 1, FIXED_HOLIDAY_1_1_ID);

        assertTrue(exist);
    }

    @Test
    void shouldReturnFalseWhenSearchingForExistingDayAndMonthAndNonexistentFixedHolidayId() {
        boolean exist = fixedHolidayRepository.existsByDayAndMonthAndNotId(1, 2, FIXED_HOLIDAY_ID_INVALID);

        assertFalse(exist);
    }

    @Test
    void shouldReturnListWhenSearchingForActiveHoliday() {
        Iterable<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByFixedHolidayIdAndIsEnable(FIXED_HOLIDAY_1_1_ID, true);

        StreamSupport.stream(fixedHolidays.spliterator(), false).forEach(fixedHoliday -> {
            assertEquals(FIXED_HOLIDAY_1_1_ID, fixedHoliday.getId());
        });
    }

    @Test
    void shouldReturnEmptyListWhenSearchingForInactiveFixedHoliday() {
        Iterable<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByFixedHolidayIdAndIsEnable(FIXED_HOLIDAY_1_1_ID, false);

        int size = StreamSupport.stream(fixedHolidays.spliterator(), false).toList().size();

        assertEquals(0, size);
    }

    @Test
    void shouldReturnListWithAllActiveHolidaysWhenNotProvidingFixedHolidayId() {
        Iterable<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByFixedHolidayIdAndIsEnable(null, true);

        int size = StreamSupport.stream(fixedHolidays.spliterator(), false).toList().size();

        assertEquals(8, size);
    }

    @Test
    void shouldReturnListWithAllInactiveHolidaysWhenNotProvidingFixedHolidayId() {
        Iterable<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByFixedHolidayIdAndIsEnable(null, false);

        int size = StreamSupport.stream(fixedHolidays.spliterator(), false).toList().size();

        assertEquals(2, size);
    }
}
