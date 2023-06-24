package br.com.insidesoftwares.dayoffmarker.validator.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.HolidayRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {HolidayValidatorBeanTest.class, HolidayValidatorBean.class})
public class HolidayValidatorBeanTest {

	@MockBean
	private HolidayRepository holidayRepository;
	@MockBean
	private DayRepository dayRepository;

	@Autowired
	private HolidayValidatorBean holidayValidatorBean;

	private static final String HOLIDAY_NAME = "name";
	private static final String HOLIDAY_DESCRIPTION = "description";
	private static final boolean HOLIDAY_OPTIONAL = false;
	private static final LocalTime HOLIDAY_FROM_TIME = LocalTime.now();
	private static final TypeHoliday HOLIDAY_HOLIDAY_TYPE = TypeHoliday.MANDATORY;
	private static final Long HOLIDAY_ID = 1L;
	private static final Long DAY_ID = 1L;

	@Test
	public void shouldntThrowExceptionWithHolidayTypeMandatoryByRunningMethodValidatorDTOParameter() {
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayID(ArgumentMatchers.anyLong())).thenReturn(false);

		holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, HOLIDAY_FROM_TIME));

		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayID(DAY_ID);
	}

	@Test
	public void shouldntThrowExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorDTOParameter()  {
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayID(ArgumentMatchers.anyLong())).thenReturn(false);

		holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, HOLIDAY_FROM_TIME));

		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayID(DAY_ID);
	}

	@Test
	public void shouldThrowExceptionDayNotExistExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				DayNotExistException.class,
				() -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
		);

		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(0)).existsByDayID(DAY_ID);
	}

	@Test
	public void shouldThrowExceptionHolidayDayExistExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayID(ArgumentMatchers.anyLong())).thenReturn(true);

		Assert.assertThrows(
				HolidayDayExistException.class,
				() -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
		);

		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayID(DAY_ID);
	}

	@Test
	public void shouldThrowExceptionHolidayFromTimeNotInformedExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorDTOParameter() {
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayID(ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				HolidayFromTimeNotInformedException.class,
				() -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, null))
		);

		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayID(DAY_ID);
	}


	@Test
	public void shouldntThrowExceptionWithHolidayTypeMandatoryByRunningMethodValidatorLongAndDTOParameter()  {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayIDAndNotId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(false);

		holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, HOLIDAY_FROM_TIME));

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayIDAndNotId(DAY_ID, HOLIDAY_ID);
	}

	@Test
	public void shouldntThrowExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorLongAndDTOParameter()  {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayIDAndNotId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(false);

		holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, HOLIDAY_FROM_TIME));

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayIDAndNotId(DAY_ID, HOLIDAY_ID);
	}

	@Test
	public void shouldThrowHolidayNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				HolidayNotExistException.class,
				() -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
		);

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
		Mockito.verify(dayRepository, Mockito.times(0)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(0)).existsByDayID(DAY_ID);
	}

	@Test
	public void shouldThrowExceptionDayNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				DayNotExistException.class,
				() -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
		);

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(0)).existsByDayID(DAY_ID);
	}

	@Test
	public void shouldThrowExceptionHolidayDayExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayIDAndNotId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(true);

		Assert.assertThrows(
				HolidayDayExistException.class,
				() -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
		);


		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayIDAndNotId(DAY_ID, HOLIDAY_ID);
	}

	@Test
	public void shouldThrowExceptionHolidayFromTimeNotInformedExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(holidayRepository.existsByDayIDAndNotId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				HolidayFromTimeNotInformedException.class,
				() -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, null))
		);

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
		Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
		Mockito.verify(holidayRepository, Mockito.times(1)).existsByDayIDAndNotId(DAY_ID, HOLIDAY_ID);
	}

	@Test
	public void shouldntThrowExceptionByRunningMethodValidatorLongParameter()  {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);

		holidayValidatorBean.validator(HOLIDAY_ID);

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
	}

	@Test
	public void shouldThrowExceptionHolidayNotExistExceptionByRunningMethodValidatorLongParameter() {
		Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				HolidayNotExistException.class,
				() -> holidayValidatorBean.validator(HOLIDAY_ID)
		);

		Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
	}

	private HolidayRequestDTO createHolidayRequestDTO(
			final TypeHoliday typeHoliday,
			final LocalTime fromTime
	){
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