package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayMonthInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class FixedHolidayValidatorBean implements Validator<Long, FixedHolidayRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;
    
    @Override
    public void validator(final FixedHolidayRequestDTO fixedHolidayRequestDTO) {
        if(
                !DateUtils.isDateValid(
                        fixedHolidayRequestDTO.day(),
                        fixedHolidayRequestDTO.month()
                )
        ) throw new DayMonthInvalidException();
        if(
                fixedHolidayRepository.existsByDayAndMonth(
                    fixedHolidayRequestDTO.day(),
                    fixedHolidayRequestDTO.month()
                )
        ) throw new FixedHolidayDayMonthCountryExistException();

    }

    @Override
    public void validator(final Long fixedHolidayId, final FixedHolidayRequestDTO fixedHolidayRequestDTO) {
        if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
        if(
                DateUtils.isDateValid(
                        fixedHolidayRequestDTO.day(),
                        fixedHolidayRequestDTO.month()
                )
        ) throw new DayMonthInvalidException();
        if(
                fixedHolidayRepository.existsByDayAndMonthAndNotId(
                        fixedHolidayRequestDTO.day(),
                        fixedHolidayRequestDTO.month(),
                        fixedHolidayId
                )
        ) throw new FixedHolidayDayMonthCountryExistException();
    }

	@Override
	public void validator(final Long fixedHolidayId) {
		if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
	}

}
