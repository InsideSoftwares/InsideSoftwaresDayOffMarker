package br.com.insidesoftwares.dayoffmarker.validator.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
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
class HolidayValidatorBeanTest {

    private static final String HOLIDAY_NAME = "name";
    private static final String HOLIDAY_DESCRIPTION = "description";
    private static final boolean HOLIDAY_OPTIONAL = false;
    private static final LocalTime HOLIDAY_FROM_TIME = LocalTime.now();
    private static final UUID HOLIDAY_ID = UUID.randomUUID();
    private static final UUID DAY_ID = UUID.randomUUID();
    @Mock
    private HolidayRepository holidayRepository;
    @Mock
    private DayRepository dayRepository;
    @InjectMocks
    private HolidayValidatorBean holidayValidatorBean;

    @Test
    void shouldntThrowExceptionWithHolidayTypeMandatoryByRunningMethodValidatorDTOParameter() {
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(true);

        holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, HOLIDAY_FROM_TIME));

        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldntThrowExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorDTOParameter() {
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(true);

        holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, HOLIDAY_FROM_TIME));

        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldThrowExceptionDayNotExistExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                DayNotExistException.class,
                () -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
        );

        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldThrowExceptionHolidayFromTimeNotInformedExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorDTOParameter() {
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(true);

        assertThrows(
                HolidayFromTimeNotInformedException.class,
                () -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, null))
        );

        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }


    @Test
    void shouldntThrowExceptionWithHolidayTypeMandatoryByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(true);

        holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, HOLIDAY_FROM_TIME));

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldntThrowExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(true);

        holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, HOLIDAY_FROM_TIME));

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldThrowHolidayNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                HolidayNotExistException.class,
                () -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
        );

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
        Mockito.verify(dayRepository, Mockito.times(0)).existsById(DAY_ID);
    }

    @Test
    void shouldThrowExceptionDayNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                DayNotExistException.class,
                () -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
        );

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldThrowExceptionHolidayFromTimeNotInformedExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(dayRepository.existsById(any(UUID.class))).thenReturn(true);

        assertThrows(
                HolidayFromTimeNotInformedException.class,
                () -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, null))
        );

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(true);

        holidayValidatorBean.validator(HOLIDAY_ID);

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
    }

    @Test
    void shouldThrowExceptionHolidayNotExistExceptionByRunningMethodValidatorLongParameter() {
        Mockito.when(holidayRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                HolidayNotExistException.class,
                () -> holidayValidatorBean.validator(HOLIDAY_ID)
        );

        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
    }

    private HolidayRequestDTO createHolidayRequestDTO(
            final TypeHoliday typeHoliday,
            final LocalTime fromTime
    ) {
        return HolidayRequestDTO.builder()
                .name(HOLIDAY_NAME)
                .description(HOLIDAY_DESCRIPTION)
                .holidayType(typeHoliday)
                .fromTime(fromTime)
                .optional(HOLIDAY_OPTIONAL)
                .dayId(DAY_ID)
                .build();
    }
}
