package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;


import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FixedHolidayUpdateValidatorBeanTest {

    @Mock
    private FixedHolidayRepository fixedHolidayRepository;

    @InjectMocks
    private FixedHolidayUpdateValidatorBean fixedHolidayUpdateValidatorBean;

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

        fixedHolidayUpdateValidatorBean.validator(1L, createFixedHolidayUpdateRequestDTO());
    }

    @Test
    void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayUpdateRequestDTO() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

        assertThrows(
                FixedHolidayNotExistException.class,
                () -> fixedHolidayUpdateValidatorBean.validator(1L, createFixedHolidayUpdateRequestDTO())
        );

    }

    @Test
    void shouldntThrowByRunningMethodValidatorWithParameterLong() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);

        fixedHolidayUpdateValidatorBean.validator(1L);
    }

    @Test
    void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLong() {
        Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

        assertThrows(
                FixedHolidayNotExistException.class,
                () -> fixedHolidayUpdateValidatorBean.validator(1L)
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
