package br.com.insidesoftwares.dayoffmarker.validator.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class HolidayValidatorBean implements Validator<UUID, HolidayRequestDTO> {

    private final HolidayRepository holidayRepository;
    private final DayRepository dayRepository;

    @Override
    public void validator(final HolidayRequestDTO holidayRequestDTO) {
        if (!dayRepository.existsById(holidayRequestDTO.dayId())) throw new DayNotExistException();
        validTypeHoliday(holidayRequestDTO);
    }

    @Override
    public void validator(final UUID holidayId, final HolidayRequestDTO holidayRequestDTO) {
        if (!holidayRepository.existsById(holidayId)) throw new HolidayNotExistException();
        if (!dayRepository.existsById(holidayRequestDTO.dayId())) throw new DayNotExistException();
        validTypeHoliday(holidayRequestDTO);
    }

    @Override
    public void validator(final UUID holidayId) {
        if (!holidayRepository.existsById(holidayId)) throw new HolidayNotExistException();
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
