package br.com.insidesoftwares.dayoffmarker.validator.fixedholiday;

import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.MethodNotImplementedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class FixedHolidayUpdateValidatorBean implements Validator<UUID, FixedHolidayUpdateRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;

    @Override
    public void validator(final FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) {
        throw new MethodNotImplementedException();
    }

    @Override
    public void validator(final UUID fixedHolidayId, final FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) {
        if (!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
    }

    @Override
    public void validator(final UUID fixedHolidayId) {
        if (!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
    }

}
