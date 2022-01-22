package br.com.sawcunha.dayoffmarker.validator.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayUpdateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.MethodNotImplementedException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.repository.FixedHolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FixedHolidayUpdateValidator implements Validator<Long, FixedHolidayUpdateRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;

    @Transactional(readOnly = true)
    @Override
    public void validator(final FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
        throw new MethodNotImplementedException();
    }

    @Transactional(readOnly = true)
    @Override
    public void validator(final Long fixedHolidayId, final FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
        if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
    }

	@Transactional(readOnly = true)
	@Override
	public void validator(final Long fixedHolidayId) throws DayOffMarkerGenericException {
		if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
	}

}
