package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayImplementationService implements DayService {

    private final DayRepository dayRepository;


    @Override
    public Day findDayByID(final Long dayID) throws Exception {
        Optional<Day> optionalDay = dayRepository.findById(dayID);
        return optionalDay.orElseThrow(DayNotExistException::new);
    }
}
