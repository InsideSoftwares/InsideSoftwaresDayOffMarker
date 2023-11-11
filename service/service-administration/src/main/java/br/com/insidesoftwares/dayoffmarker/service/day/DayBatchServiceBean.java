package br.com.insidesoftwares.dayoffmarker.service.day;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Month;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.day.DayBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
class DayBatchServiceBean implements DayBatchService {

    private final DayRepository dayRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CompletableFuture<Boolean> createDayBatch(final Month month, final int year) {
        log.info("Criando os dias do mes: {} e ano: {}", month, year);
        boolean success = true;
        List<Day> dayBatches = new ArrayList<>();

        LocalDate dateBase = LocalDate.of(year, month.getNumberOfMonth(), 1);

        while (dateBase.getYear() == year && dateBase.getMonthValue() == month.getNumberOfMonth()) {
            if (!existDayInDay(dateBase)) {
                Day day = Day.builder()
                        .date(dateBase)
                        .isWeekend(DateUtils.isWeenkend(dateBase))
                        .dayOfYear(dateBase.getDayOfYear())
                        .dayOfWeek(dateBase.getDayOfWeek())
                        .isHoliday(false)
                        .build();

                dayBatches.add(day);
            }
            dateBase = dateBase.plusDays(1);
        }

        try {
            if(!dayBatches.isEmpty()) {
                log.info("Salvando os dias do mes: {} e ano: {} - Total: {} dias", month, year, dayBatches.size());

                dayRepository.saveAllAndFlush(dayBatches);
            }
        } catch (Exception exception) {
            log.error("createDayBatch - Error", exception);

            success = false;
        }

        return CompletableFuture.completedFuture(success);
    }

    public boolean existDayInDay(LocalDate day) {
        return dayRepository.existsByDate(day);
    }
}
