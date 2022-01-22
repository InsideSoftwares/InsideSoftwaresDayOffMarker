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
@RequiredArgsConstructor
public class FixedHolidayValidator implements Validator<Long, FixedHolidayRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    @Override
    public void validator(FixedHolidayRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
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

    @Transactional(readOnly = true)
    @Override
    public void validator(Long fixedHolidayId, FixedHolidayRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
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

}
