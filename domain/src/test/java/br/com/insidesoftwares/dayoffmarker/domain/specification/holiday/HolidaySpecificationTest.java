package br.com.insidesoftwares.dayoffmarker.domain.specification.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HolidaySpecificationTest {

    @Test
    void shouldGenerateErrorWhenPassingStartDateGreaterThanEndDate() {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        assertThrows(StartDateAfterEndDateException.class, () -> HolidaySpecification.findAllByStartDateAndEndDate(startDate, endDate));

    }


}
