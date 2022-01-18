package br.com.sawcunha.dayoffmarker.validator.fixedholiday;

import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayUpdateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.repository.FixedHolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.el.MethodNotFoundException;

@Component
@RequiredArgsConstructor
public class FixedHolidayUpdateValidator implements Validator<Long, FixedHolidayUpdateRequestDTO> {

    private final FixedHolidayRepository fixedHolidayRepository;

    @Transactional(readOnly = true)
    @Override
    public void validator(FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) throws Exception {
        throw new MethodNotFoundException();
    }

    @Transactional(readOnly = true)
    @Override
    public void validator(Long fixedHolidayId, FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO) throws Exception {
        if(!fixedHolidayRepository.existsById(fixedHolidayId)) throw new FixedHolidayNotExistException();
    }

}