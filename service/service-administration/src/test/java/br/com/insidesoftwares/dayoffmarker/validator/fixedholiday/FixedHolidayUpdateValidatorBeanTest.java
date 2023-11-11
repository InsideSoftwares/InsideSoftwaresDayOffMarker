package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;


import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.MethodNotImplementedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FixedHolidayUpdateValidatorBeanTest {

    @Mock
    private FixedHolidayRepository fixedHolidayRepository;
    @InjectMocks
    private FixedHolidayUpdateValidatorBean fixedHolidayUpdateValidatorBean;

    private static final UUID FIXED_HOLIDAY_ID = UUID.randomUUID();

    @Test
    void shouldThrowMethodNotImplementedExceptionByRunningMethodValidatorWithParameterFixedHolidayUpdateRequestDTO() {
        assertThrows(
                MethodNotImplementedException.class,
                () -> fixedHolidayUpdateValidatorBean.validator(FixedHolidayUpdateRequestDTO.builder().build())
        );
    }

    @Test
    void shouldntThrowByRunningMethodValidatorWithParameterLongAndFixedHolidayUpdateRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);

        fixedHolidayUpdateValidatorBean.validator(FIXED_HOLIDAY_ID, createFixedHolidayUpdateRequestDTO());
    }

    @Test
    void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayUpdateRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

        assertThrows(
                FixedHolidayNotExistException.class,
                () -> fixedHolidayUpdateValidatorBean.validator(FIXED_HOLIDAY_ID, createFixedHolidayUpdateRequestDTO())
        );

    }

    @Test
    void shouldntThrowByRunningMethodValidatorWithParameterLong() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);

        fixedHolidayUpdateValidatorBean.validator(FIXED_HOLIDAY_ID);
    }

    @Test
    void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLong() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

        assertThrows(
                FixedHolidayNotExistException.class,
                () -> fixedHolidayUpdateValidatorBean.validator(FIXED_HOLIDAY_ID)
        );
    }

    private FixedHolidayUpdateRequestDTO createFixedHolidayUpdateRequestDTO() {
        return FixedHolidayUpdateRequestDTO.builder()
                .name("Name")
                .description("Description")
                .fromTime(LocalTime.now())
                .isEnable(true)
                .isOptional(false)
                .build();
    }

}
