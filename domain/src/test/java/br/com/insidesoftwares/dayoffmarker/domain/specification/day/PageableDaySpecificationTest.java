package br.com.insidesoftwares.dayoffmarker.domain.specification.day;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderDay;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class PageableDaySpecificationTest extends RepositoryTestUtils {

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
    void shouldGenerateErrorWhenPassingStartDateGreaterThanEndDate() {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        assertThrows(StartDateAfterEndDateException.class, () -> PageableDaySpecification.findAllPageableByStartDateAndEndDate(startDate, endDate));

    }

    @Test
    void shouldReturnAllDaysOfNovember() {
        LocalDate startDate = LocalDate.of(2023, 11, 1);
        LocalDate endDate = LocalDate.of(2023, 11, 30);

        Specification<Day> daySpecification = PageableDaySpecification.findAllPageableByStartDateAndEndDate(startDate, endDate);
        Page<Day> days = dayRepository.findAll(daySpecification, createPageable());

        assertEquals(1, days.getTotalPages());
        assertEquals(30, days.getTotalElements());
        assertEquals(30, days.getContent().size());
    }

    @Test
    void shouldReturnAllDaysStartingFromNovember2023() {
        LocalDate startDate = LocalDate.of(2023, 11, 1);

        Specification<Day> daySpecification = PageableDaySpecification.findAllPageableByStartDateAndEndDate(startDate, null);
        Page<Day> days = dayRepository.findAll(daySpecification, createPageable());

        assertEquals(3, days.getTotalPages());
        assertEquals(61, days.getTotalElements());
        assertEquals(30, days.getContent().size());
    }

    @Test
    void shouldReturnAllDaysBeforeNovember2023() {
        LocalDate endDate = LocalDate.of(2023, 11, 1);

        Specification<Day> daySpecification = PageableDaySpecification.findAllPageableByStartDateAndEndDate(null, endDate);
        Page<Day> days = dayRepository.findAll(daySpecification, createPageable());

        assertEquals(11, days.getTotalPages());
        assertEquals(305, days.getTotalElements());
        assertEquals(30, days.getContent().size());
        assertFalse(
                days.getContent().stream().anyMatch(day -> day.getDate().isEqual(endDate))
        );
    }

    private Pageable createPageable() {
        InsidePaginationFilterDTO paginationFilter = InsidePaginationFilterDTO.builder()
                .sizePerPage(30)
                .page(1)
                .order(eOrderDay.DATE.name())
                .direction(Sort.Direction.ASC)
                .build();


        return PaginationUtils.createPageable(paginationFilter, eOrderHoliday.ID);
    }

}
