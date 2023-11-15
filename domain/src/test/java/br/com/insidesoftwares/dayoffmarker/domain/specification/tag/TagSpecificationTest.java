package br.com.insidesoftwares.dayoffmarker.domain.specification.tag;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class TagSpecificationTest extends RepositoryTestUtils {

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
    void shouldReturnAllFridaysOfTheYear() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                null,
                null,
                null,
                null
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
    }

    @Test
    void shouldReturnAllFridayThe13thsOfTheYear() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                null,
                13,
                null,
                null
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
        days.forEach(day -> assertEquals(13, day.getDate().getDayOfMonth()));
    }

    @Test
    void shouldReturnFridayThe13thOnDay286OfYear() {
        LocalDate expectedDate = LocalDate.of(2023,10, 13);

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                286,
                13,
                null,
                null
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
        days.forEach(day -> assertEquals(expectedDate, day.getDate()));
    }

    @Test
    void shouldReturnFridayThe13thOfMonth1() {
        LocalDate expectedDate = LocalDate.of(2023,1, 13);

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                null,
                13,
                1,
                null
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
        days.forEach(day -> assertEquals(expectedDate, day.getDate()));
    }

    @Test
    void shouldReturnFridayOfMonth1() {

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                null,
                null,
                1,
                null
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
    }

    @Test
    void shouldReturnFridayOfTheYear2023() {

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                null,
                null,
                null,
                2023
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
    }

    @Test
    void shouldReturnMondayOfMonth5Year2023() {

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.MONDAY,
                null,
                null,
                5,
                2023
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.MONDAY, day.getDayOfWeek()));
    }

    @Test
    void shouldReturnFridayThe13thOfMonth1OfYear2023() {
        LocalDate expectedDate = LocalDate.of(2023,1, 13);

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.FRIDAY,
                null,
                13,
                1,
                2023
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertFalse(days.isEmpty());
        days.forEach(day -> assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek()));
        days.forEach(day -> assertEquals(expectedDate, day.getDate()));
    }

    @Test
    void shouldReturnEmptyListWhenSearchingForSundayThe13thOfMonth1OfYear2024() {

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkUnlinkRequestDTO(
                DayOfWeek.SUNDAY,
                null,
                13,
                1,
                2024
        );

        Specification<Day> daySpecification = TagSpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        List<Day> days = dayRepository.findAll(daySpecification);

        assertTrue(days.isEmpty());
    }

    private TagLinkUnlinkRequestDTO createTagLinkUnlinkRequestDTO(
            final DayOfWeek dayOfWeek,
            final Integer dayOfYear,
            final Integer day,
            final Integer month,
            final Integer year
    ) {
        return TagLinkUnlinkRequestDTO.builder()
                .tagsID(Set.of())
                .dayOfWeek(dayOfWeek)
                .dayOfYear(dayOfYear)
                .day(day)
                .month(month)
                .year(year)
                .build();
    }

}
