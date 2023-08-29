package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayMonthInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.holiday.FixedHolidayRepository;
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
class FixedHolidayValidatorBeanTest {

	@Mock
	private FixedHolidayRepository fixedHolidayRepository;

	@InjectMocks
	private FixedHolidayValidatorBean fixedHolidayValidatorBean;

	@Test
	void shouldntThrowByRunningMethodValidatorWithParameterFixedHolidayRequestDTO() {
		Mockito.when(
			fixedHolidayRepository.existsByDayAndMonth(ArgumentMatchers.any(), ArgumentMatchers.any())
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
			fixedHolidayRepository.existsByDayAndMonth(ArgumentMatchers.any(), ArgumentMatchers.any())
		).thenReturn(true);

		assertThrows(
			FixedHolidayDayMonthCountryExistException.class,
			() -> fixedHolidayValidatorBean.validator(createFixedHolidayRequestDTO(29, 2))
		);

	}

	@Test
	void shouldntThrowByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
		Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(
			fixedHolidayRepository.existsByDayAndMonthAndNotId(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
		).thenReturn(false);

		fixedHolidayValidatorBean.validator(1L, createFixedHolidayRequestDTO(1, 1));
	}

	@Test
	void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
		Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

		assertThrows(
			FixedHolidayNotExistException.class,
			() -> fixedHolidayValidatorBean.validator(1L, createFixedHolidayRequestDTO(1, 1))
		);

	}

	@Test
	void shouldThrowDayMonthInvalidExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
		Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);

		assertThrows(
			DayMonthInvalidException.class,
			() -> fixedHolidayValidatorBean.validator(1L, createFixedHolidayRequestDTO(30, 2))
		);

	}

	@Test
	void shouldThrowFixedHolidayDayMonthCountryExistExceptionByRunningMethodValidatorWithParameterLongAndFixedHolidayRequestDTO() {
		Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(
			fixedHolidayRepository.existsByDayAndMonthAndNotId(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
		).thenReturn(true);

		assertThrows(
			FixedHolidayDayMonthCountryExistException.class,
			() -> fixedHolidayValidatorBean.validator(1L, createFixedHolidayRequestDTO(29, 2))
		);

	}

	@Test
	void shouldntThrowByRunningMethodValidatorWithParameterLong() {
		Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);

		fixedHolidayValidatorBean.validator(1L);
	}

	@Test
	void shouldThrowFixedHolidayNotExistExceptionByRunningMethodValidatorWithParameterLong() {
		Mockito.when(fixedHolidayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

		assertThrows(
			FixedHolidayNotExistException.class,
			() -> fixedHolidayValidatorBean.validator(1L)
		);

	}

	private FixedHolidayRequestDTO createFixedHolidayRequestDTO(final Integer day, final Integer month) {
		return FixedHolidayRequestDTO.builder()
			.name("Name")
			.description("Description")
			.fromTime(LocalTime.now())
			.day(day)
			.month(month)
			.isEnable(true)
			.isOptional(false)
			.build();
	}
}
