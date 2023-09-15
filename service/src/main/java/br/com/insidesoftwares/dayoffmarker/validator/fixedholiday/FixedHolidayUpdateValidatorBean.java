package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.MethodNotImplementedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class FixedHolidayUpdateValidatorBean implements Validator<Long, FixedHolidayUpdateRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;

    @Override
    public void validator(final FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) {
        throw new MethodNotImplementedException();
    }
    
    @Override
    public void validator(final Long fixedHolidayId, final FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) {
        if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
    }
	
	@Override
	public void validator(final Long fixedHolidayId) {
		if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
	}

}
