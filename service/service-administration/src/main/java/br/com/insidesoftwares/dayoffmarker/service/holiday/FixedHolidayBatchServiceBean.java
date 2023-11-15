package br.com.insidesoftwares.dayoffmarker.service.holiday;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.holiday.HolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.holiday.FixedHolidayBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static br.com.insidesoftwares.dayoffmarker.domain.specification.holiday.FixedHolidaySpecification.findDayWithoutHolidaysByDayAndMonthAndYearAndFixedHolidayIdOrNotHoliday;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixedHolidayBatchServiceBean implements FixedHolidayBatchService {

    private final HolidayRepository holidayRepository;
    private final DayRepository dayRepository;
    private final HolidayMapper holidayMapper;

    @Async
    @Override
    public CompletableFuture<Boolean> createHolidayBatch(FixedHoliday fixedHoliday) {
        boolean success = true;

        try {
            Specification<Day> daySpecification = findDayWithoutHolidaysByDayAndMonthAndYearAndFixedHolidayIdOrNotHoliday(
                    fixedHoliday.getDay(), fixedHoliday.getMonth(), fixedHoliday.getId()
            );

            List<Day> days = dayRepository.findAll(daySpecification);

            List<Holiday> holidays = days.parallelStream()
                    .map(day -> holidayMapper.toEntity(fixedHoliday, day))
                    .toList();

            if(!holidays.isEmpty()) {
                log.info("Salvando o feriado: {}", fixedHoliday.getName());

                holidayRepository.saveAllAndFlush(holidays);
            }
        } catch (Exception exception) {
            log.error("createHolidayBatch - Error", exception);

            success = false;
        }

        return CompletableFuture.completedFuture(success);
    }
}
