package br.com.sawcunha.dayoffmarker.validator.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayMonthInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.repository.FixedHolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class FixedHolidayValidatorBean implements Validator<Long, FixedHolidayRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;
    private final CountryRepository countryRepository;
    
    @Override
    public void validator(final FixedHolidayRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
        if(!countryRepository.existsById(fixedHolidayRequestDTO.getCountryId())) throw new CountryNotExistException();
        if(
                DateUtils.isDateValid(
                        fixedHolidayRequestDTO.getDay(),
                        fixedHolidayRequestDTO.getMonth()
                )
        ) throw new DayMonthInvalidException();
        if(
                fixedHolidayRepository.existsByDayAndMonthAndCountryId(
                    fixedHolidayRequestDTO.getDay(),
                    fixedHolidayRequestDTO.getMonth(),
                    fixedHolidayRequestDTO.getCountryId()
                )
        ) throw new FixedHolidayDayMonthCountryExistException();

    }

    @Override
    public void validator(final Long fixedHolidayId, final FixedHolidayRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
        if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
        if(!countryRepository.existsById(fixedHolidayRequestDTO.getCountryId())) throw new CountryNotExistException();
        if(
                DateUtils.isDateValid(
                        fixedHolidayRequestDTO.getDay(),
                        fixedHolidayRequestDTO.getMonth()
                )
        ) throw new DayMonthInvalidException();
        if(
                fixedHolidayRepository.existsByDayAndMonthAndCountryIdAndNotId(
                        fixedHolidayRequestDTO.getDay(),
                        fixedHolidayRequestDTO.getMonth(),
                        fixedHolidayRequestDTO.getCountryId(),
                        fixedHolidayId
                )
        ) throw new FixedHolidayDayMonthCountryExistException();
    }

	@Override
	public void validator(final Long fixedHolidayId) throws DayOffMarkerGenericException {
		if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
	}

}
