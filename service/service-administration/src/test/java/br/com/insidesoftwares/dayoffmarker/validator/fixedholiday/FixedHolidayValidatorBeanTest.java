package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayMonthInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FixedHolidayValidatorBeanTest {

    @Mock
    private FixedHolidayRepository fixedHolidayRepository;

    @InjectMocks
    private FixedHolidayValidatorBean fixedHolidayValidatorBean;

    private static final UUID FIXED_HOLIDAY_ID = UUID.randomUUID();

    @Test
    void shouldntThrowByRunningMethodValidatorWithParameterFixedHolidayRequestDTO() {
        Mockito.when(
                fixedHolidayRepository.existsByDayAndMonth(any(int.class), any(int.class))
        ).thenReturn(false);

        fixedHolidayValidatorBean.validator(createFixedHolidayRequestDTO(1, 1));
    }

    @Test
    void shouldThrowDayMonthInvalidExceptionByRunningMethodValidatorWithParameterFixedHolidayRequestDTO() {
        assertThrows(
                DayMonthInvalidException.class,
                () -> fixedHolidayValidatorBean.validator(createFixedHolidayRequestDTO(30, 2))
        );

    }

    @Test
    void shouldThrowFixedHolidayDayMonthCountryExistExceptionByRunningMethodValidatorWithParameterFixedHolidayRequestDTO() {
        Mockito.when(
                fixedHolidayRepository.existsByDayAndMonth(any(int.class), any(int.class))
        ).thenReturn(true);

        assertThrows(
                FixedHolidayDayMonthCountryExistException.class,
                () -> fixedHolidayValidatorBean.validator(createFixedHolidayRequestDTO(29, 2))
        );

    }

    @Test
    void shouldntThrowByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(any())).thenReturn(true);
        Mockito.when(
                fixedHolidayRepository.existsByDayAndMonthAndNotId(any(int.class), any(int.class), any())
        ).thenReturn(false);

        fixedHolidayValidatorBean.validator(FIXED_HOLIDAY_ID, createFixedHolidayRequestDTO(1, 1));
    }

    @Test
    void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(any())).thenReturn(false);

        assertThrows(
                FixedHolidayNotExistException.class,
                () -> fixedHolidayValidatorBean.validator(FIXED_HOLIDAY_ID, createFixedHolidayRequestDTO(1, 1))
        );

    }

    @Test
    void shouldThrowDayMonthInvalidExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(any())).thenReturn(true);

        assertThrows(
                DayMonthInvalidException.class,
                () -> fixedHolidayValidatorBean.validator(FIXED_HOLIDAY_ID, createFixedHolidayRequestDTO(30, 2))
        );

    }

    @Test
    void shouldThrowFixedHolidayDayMonthCountryExistExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(any())).thenReturn(true);
        Mockito.when(
                fixedHolidayRepository.existsByDayAndMonthAndNotId(any(int.class), any(int.class), any())
        ).thenReturn(true);

        assertThrows(
                FixedHolidayDayMonthCountryExistException.class,
                () -> fixedHolidayValidatorBean.validator(FIXED_HOLIDAY_ID, createFixedHolidayRequestDTO(29, 2))
        );

    }

    @Test
    void shouldntThrowByRunningMethodValidatorWithParameterLong() {
        Mockito.when(fixedHolidayRepository.existsById(any())).thenReturn(true);

        fixedHolidayValidatorBean.validator(FIXED_HOLIDAY_ID);
    }

    @Test
    void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLong() {
        Mockito.when(fixedHolidayRepository.existsById(any())).thenReturn(false);

        assertThrows(
                FixedHolidayNotExistException.class,
                () -> fixedHolidayValidatorBean.validator(FIXED_HOLIDAY_ID)
        );

    }

    private FixedHolidayRequestDTO createFixedHolidayRequestDTO(final Integer day, final Integer month) {
        return FixedHolidayRequestDTO.builder()
                .name("Name")
                .description("Description")
                .fromTime(LocalTime.now())
                .day(day)
                .month(month)
                .enable(true)
                .optional(false)
                .build();
    }
}
