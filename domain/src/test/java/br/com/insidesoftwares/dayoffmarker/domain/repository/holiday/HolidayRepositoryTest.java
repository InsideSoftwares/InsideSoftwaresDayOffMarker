package br.com.insidesoftwares.dayoffmarker.domain.repository.holiday;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
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

import static br.com.insidesoftwares.dayoffmarker.domain.specification.holiday.HolidaySpecification.findAllByStartDateAndEndDate;
import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class HolidayRepositoryTest extends RepositoryTestUtils {

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
    private HolidayRepository holidayRepository;

    @Test
    void shouldReturnHolidaysOfNovember() {

        LocalDate startDate = LocalDate.of(2023, 11, 1);
        LocalDate endDate = LocalDate.of(2023, 11, 30);
        Specification<Holiday> holidaySpecification = findAllByStartDateAndEndDate(startDate, endDate);

        Page<Holiday> holidays = holidayRepository.findAll(holidaySpecification, createPageable());

        assertEquals(1, holidays.getTotalPages());
        assertEquals(3, holidays.getTotalElements());
        assertEquals(3, holidays.getContent().size());
    }

    @Test
    void shouldReturnHolidaysAfterNovember() {

        LocalDate startDate = LocalDate.of(2023, 11, 1);
        Specification<Holiday> holidaySpecification = findAllByStartDateAndEndDate(startDate, null);

        Page<Holiday> holidays = holidayRepository.findAll(holidaySpecification, createPageable());

        assertEquals(1, holidays.getTotalPages());
        assertEquals(6, holidays.getTotalElements());
        assertEquals(6, holidays.getContent().size());
    }

    @Test
    void shouldReturnHolidaysBeforeApril() {

        LocalDate endDate = LocalDate.of(2023, 4, 1);
        Specification<Holiday> holidaySpecification = findAllByStartDateAndEndDate(null, endDate);

        Page<Holiday> holidays = holidayRepository.findAll(holidaySpecification, createPageable());

        assertEquals(1, holidays.getTotalPages());
        assertEquals(1, holidays.getTotalElements());
        assertEquals(1, holidays.getContent().size());
    }

    private Pageable createPageable() {
        InsidePaginationFilterDTO paginationFilter = InsidePaginationFilterDTO.builder()
                .sizePerPage(30)
                .page(1)
                .order(eOrderHoliday.DAY.name())
                .direction(Sort.Direction.ASC)
                .build();


        return PaginationUtils.createPageable(paginationFilter, eOrderHoliday.ID);
    }

}
