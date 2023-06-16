package br.com.insidesoftwares.dayoffmarker.validator.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class HolidayValidatorBean implements Validator<Long, HolidayRequestDTO> {

    private final HolidayRepository holidayRepository;
    private final DayRepository dayRepository;

    @Override
    public void validator(final HolidayRequestDTO holidayRequestDTO) {
        if(!dayRepository.existsById(holidayRequestDTO.dayId())) throw new DayNotExistException();
        if(holidayRepository.existsByDayID(holidayRequestDTO.dayId())) throw new HolidayDayExistException();
        validTypeHoliday(holidayRequestDTO);
    }

    @Override
    public void validator(final Long holidayId,final  HolidayRequestDTO holidayRequestDTO) {
        if(!holidayRepository.existsById(holidayId)) throw new HolidayNotExistException();
        if(!dayRepository.existsById(holidayRequestDTO.dayId())) throw new DayNotExistException();
        if(holidayRepository.existsByDayIDAndNotId(holidayRequestDTO.dayId(),holidayId)) throw new HolidayDayExistException();
        validTypeHoliday(holidayRequestDTO);
    }

	@Override
	public void validator(final Long holidayId) {
		if(!holidayRepository.existsById(holidayId)) throw new HolidayNotExistException();
	}

	private void validTypeHoliday(final HolidayRequestDTO holidayRequestDTO) throws HolidayFromTimeNotInformedException {
		if (
				holidayRequestDTO.holidayType() == TypeHoliday.HALF_PERIOD &&
						Objects.isNull(holidayRequestDTO.fromTime())
		) {
			throw new HolidayFromTimeNotInformedException();
		}
    }
}
