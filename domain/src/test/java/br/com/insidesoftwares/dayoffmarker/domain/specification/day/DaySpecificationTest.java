package br.com.insidesoftwares.dayoffmarker.domain.specification.day;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.EndDateAfterStartDateException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class DaySpecificationTest extends RepositoryTestUtils {

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
    void shouldReturnNovemberEleventhWithoutProvidingTagIdAndTagCode() {
        LocalDate expectedDate = LocalDate.of(2023,11,1);

        Specification<Day> daySpecification = DaySpecification.findDayByDateOrTag(expectedDate, null, null);
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
    }

    @Test
    void shouldReturnNovemberEleventhWhenProvidingTagIdAndPassingNullForTagCode() {
        LocalDate expectedDate = LocalDate.of(2023,11,1);

        Specification<Day> daySpecification = DaySpecification.findDayByDateOrTag(expectedDate, TAG_ID_AKLA, null);
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
        day.get().getTags().forEach(tag -> assertEquals(TAG_ID_AKLA, tag.getId()));
    }

    @Test
    void shouldReturnNovemberEleventhWhenProvidingTagCodeAndPassingNullForTagId() {
        LocalDate expectedDate = LocalDate.of(2023,11,1);

        Specification<Day> daySpecification = DaySpecification.findDayByDateOrTag(expectedDate, null, "AKLA");
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
        day.get().getTags().forEach(tag -> assertEquals(TAG_ID_AKLA, tag.getId()));
    }

    @Test
    void shouldReturnNovemberEleventhWhenProvidingTagCodeAndTagId() {
        LocalDate expectedDate = LocalDate.of(2023,11,1);

        Specification<Day> daySpecification = DaySpecification.findDayByDateOrTag(expectedDate, TAG_ID_AKLA, "AKLA");
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
        day.get().getTags().forEach(tag -> assertEquals(TAG_ID_AKLA, tag.getId()));
    }

    @Test
    void shouldReturnAllDaysThatAreOnlyHolidaysWithoutProvidingStartDateAndEndDate() {

        Specification<Day> daySpecification = DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                null,
                null,
                true,
                false,
                false
        );
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        assertEquals(6, days.size());
    }

    @Test
    void shouldReturnAllDaysThatAreHolidaysAndWeekendsWithoutProvidingStartDateAndEndDate() {

        Specification<Day> daySpecification = DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                null,
                null,
                true,
                true,
                false
        );
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        assertEquals(1, days.size());
    }

    @Test
    void shouldReturnAllDaysThatAreHolidaysAndWeekendsWithProvidedStartDateAndEndDate() {

        Specification<Day> daySpecification = DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,31),
                true,
                true,
                true
        );
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        assertEquals(1, days.size());
    }

    @Test
    void shouldReturnAllDaysThatAreOnlyHolidaysWithProvidedStartDateAndEndDate() {

        Specification<Day> daySpecification = DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                LocalDate.of(2023,11,1),
                LocalDate.of(2023,11,30),
                true,
                false,
                true
        );
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        assertEquals(2, days.size());
    }

    @Test
    void shouldReturnAllDaysThatAreOnlyHolidaysWithoutProvidingStartDateAndEndDateAndSearchFutureDatesFalse() {

        Specification<Day> daySpecification = DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                LocalDate.of(2023,11,30),
                LocalDate.of(2023,11,1),
                true,
                false,
                false
        );
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        assertEquals(2, days.size());
    }

    @Test
    void shouldReturnErrorWhenPassingStartDateGreaterThanEndDate() {
        assertThrows(StartDateAfterEndDateException.class, () -> DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                LocalDate.of(2023,11,30),
                LocalDate.of(2023,11,1),
                true,
                false,
                true
        ));
    }

    @Test
    void shouldReturnErrorWhenPassingEndDateGreaterThanStartDate() {
        assertThrows(EndDateAfterStartDateException.class, () -> DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                LocalDate.of(2023,11,1),
                LocalDate.of(2023,11,30),
                true,
                false,
                false
        ));
    }

    @Test
    void shouldReturnNovemberFifteenthWhenProvidingHolidayAndNotWeekend() {
        LocalDate expectedDate = LocalDate.of(2023,11,15);

        Specification<Day> daySpecification = DaySpecification.findWorkingDayByDateAndIsHolidayAndIsWeekend(
                expectedDate,
                true,
                false
        );
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
    }

    @Test
    void shouldReturnNovemberFifteenthWhenProvidingHolidayAndWeekendNull() {
        LocalDate expectedDate = LocalDate.of(2023,11,15);

        Specification<Day> daySpecification = DaySpecification.findWorkingDayByDateAndIsHolidayAndIsWeekend(
                expectedDate,
                true,
                null
        );
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
    }

    @Test
    void shouldReturnNovemberTwelfthWhenProvidingHolidayFalseAndWeekendTrue() {
        LocalDate expectedDate = LocalDate.of(2023,11,12);

        Specification<Day> daySpecification = DaySpecification.findWorkingDayByDateAndIsHolidayAndIsWeekend(
                expectedDate,
                false,
                true
        );
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
    }

    @Test
    void shouldReturnNovemberTwelfthWhenProvidingHolidayNullAndWeekendTrue() {
        LocalDate expectedDate = LocalDate.of(2023,11,12);

        Specification<Day> daySpecification = DaySpecification.findWorkingDayByDateAndIsHolidayAndIsWeekend(
                expectedDate,
                null,
                true
        );
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
    }

    @Test
    void shouldReturnNovemberTwelfthWhenProvidingHolidayNullAndWeekendNull() {
        LocalDate expectedDate = LocalDate.of(2023,11,12);

        Specification<Day> daySpecification = DaySpecification.findWorkingDayByDateAndIsHolidayAndIsWeekend(
                expectedDate,
                null,
                true
        );
        Optional<Day> day = dayRepository.findOne(daySpecification);

        assertTrue(day.isPresent());
        assertEquals(expectedDate, day.get().getDate());
    }
}
