package br.com.insidesoftwares.dayoffmarker.validator.holiday;

import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class HolidayValidatorBeanTest {

    private static final String HOLIDAY_NAME = "name";
    private static final String HOLIDAY_DESCRIPTION = "description";
    private static final boolean HOLIDAY_OPTIONAL = false;
    private static final LocalTime HOLIDAY_FROM_TIME = LocalTime.now();
    private static final Long HOLIDAY_ID = 1L;
    private static final Long DAY_ID = 1L;
    @Mock
    private HolidayRepository holidayRepository;
    @Mock
    private DayRepository dayRepository;
    @InjectMocks
    private HolidayValidatorBean holidayValidatorBean;

//    @Test
//    public void shouldntThrowExceptionWithHolidayTypeMandatoryByRunningMethodValidatorDTOParameter() {
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, HOLIDAY_FROM_TIME));
//
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldntThrowExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorDTOParameter() {
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, HOLIDAY_FROM_TIME));
//
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldThrowExceptionDayNotExistExceptionByRunningMethodValidatorDTOParameter() {
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
//
//        assertThrows(
//                DayNotExistException.class,
//                () -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
//        );
//
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldThrowExceptionHolidayFromTimeNotInformedExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorDTOParameter() {
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        assertThrows(
//                HolidayFromTimeNotInformedException.class,
//                () -> holidayValidatorBean.validator(createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, null))
//        );
//
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//
//    @Test
//    public void shouldntThrowExceptionWithHolidayTypeMandatoryByRunningMethodValidatorLongAndDTOParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, HOLIDAY_FROM_TIME));
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldntThrowExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorLongAndDTOParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, HOLIDAY_FROM_TIME));
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldThrowHolidayNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
//
//        assertThrows(
//                HolidayNotExistException.class,
//                () -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
//        );
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//        Mockito.verify(dayRepository, Mockito.times(0)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldThrowExceptionDayNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
//
//        assertThrows(
//                DayNotExistException.class,
//                () -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.MANDATORY, null))
//        );
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldThrowExceptionHolidayFromTimeNotInformedExceptionWithHolidayTypeHalfPeriodByRunningMethodValidatorLongAndDTOParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//        Mockito.when(dayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        assertThrows(
//                HolidayFromTimeNotInformedException.class,
//                () -> holidayValidatorBean.validator(HOLIDAY_ID, createHolidayRequestDTO(TypeHoliday.HALF_PERIOD, null))
//        );
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//        Mockito.verify(dayRepository, Mockito.times(1)).existsById(DAY_ID);
//    }
//
//    @Test
//    public void shouldntThrowExceptionByRunningMethodValidatorLongParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
//
//        holidayValidatorBean.validator(HOLIDAY_ID);
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//    }
//
//    @Test
//    public void shouldThrowExceptionHolidayNotExistExceptionByRunningMethodValidatorLongParameter() {
//        Mockito.when(holidayRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
//
//        assertThrows(
//                HolidayNotExistException.class,
//                () -> holidayValidatorBean.validator(HOLIDAY_ID)
//        );
//
//        Mockito.verify(holidayRepository, Mockito.times(1)).existsById(HOLIDAY_ID);
//    }
//
//    private HolidayRequestDTO createHolidayRequestDTO(
//            final TypeHoliday typeHoliday,
//            final LocalTime fromTime
//    ) {
//        return HolidayRequestDTO.builder()
//                .name(HOLIDAY_NAME)
//                .description(HOLIDAY_DESCRIPTION)
//                .holidayType(typeHoliday)
//                .fromTime(fromTime)
//                .optional(HOLIDAY_OPTIONAL)
//                .dayId(DAY_ID)
//                .build();
//    }
}
