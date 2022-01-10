package br.com.sawcunha.dayoffmarker.validator.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayMonthInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.repository.FixedHolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FixedHolidayValidator implements Validator<Long, FixedHolidayRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;
    private final CountryRepository countryRepository;

    @Override
    public void validator(FixedHolidayRequestDTO fixedHolidayRequestDTO) throws Exception {
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
    public void validator(Long fixedHolidayId, FixedHolidayRequestDTO fixedHolidayRequestDTO) throws Exception {
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
