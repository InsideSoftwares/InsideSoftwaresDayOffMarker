package br.com.sawcunha.dayoffmarker.validator.holiday;

import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.repository.HolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class HolidayValidator implements Validator<Long, HolidayRequestDTO> {

    private final HolidayRepository holidayRepository;
    private final DayRepository dayRepository;

    @Transactional(readOnly = true)
    @Override
    public void validator(final HolidayRequestDTO holidayRequestDTO) throws Exception {
        if(!dayRepository.existsById(holidayRequestDTO.getDayId())) throw new DayNotExistException();
        if(holidayRepository.existsByDayID(holidayRequestDTO.getDayId())) throw new HolidayDayExistException();
        validTypeHoliday(holidayRequestDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public void validator(final Long holidayId,final  HolidayRequestDTO holidayRequestDTO) throws Exception {
        if(!holidayRepository.existsById(holidayId)) throw new HolidayNotExistException();
        if(!dayRepository.existsById(holidayRequestDTO.getDayId())) throw new DayNotExistException();
        if(holidayRepository.existsByDayIDAndNotId(holidayRequestDTO.getDayId(),holidayId)) throw new HolidayDayExistException();
        validTypeHoliday(holidayRequestDTO);
    }

    private void validTypeHoliday(final HolidayRequestDTO holidayRequestDTO) throws Exception{
        switch (holidayRequestDTO.getHolidayType()){
            case OPTIONAL, HALF_PERIOD -> {
                if(Objects.isNull(holidayRequestDTO.getFromTime()))
                    throw new HolidayFromTimeNotInformedException();
            }
        }
    }
}
