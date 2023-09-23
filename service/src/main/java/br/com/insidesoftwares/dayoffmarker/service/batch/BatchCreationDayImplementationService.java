package br.com.insidesoftwares.dayoffmarker.service.batch;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayBatchRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class BatchCreationDayImplementationService implements BatchCreationDayService {

    private final DayBatchRepository dayBatchRepository;
    private final DayRepository dayRepository;

    @Override
    public void createDaysBatch(List<DayBatch> daysBatch) {
        log.info("Create Days Batch");
        daysBatch.forEach(dayBatch -> {
            if (!dayBatchRepository.existsByDate(dayBatch.getDate())) {
                dayBatchRepository.saveAndFlush(dayBatch);
            } else {
                log.info("Day Batch exist {}", dayBatch.getDate());
            }
        });
    }

    @Override
    public void deleteDayBatch(DayBatch dayBatch) {
        dayBatchRepository.delete(dayBatch);
    }


    @Override
    public void createDays(List<Day> days) {
        log.info("Create Days");
        days.forEach(day -> {
            if (!dayRepository.existsByDate(day.getDate())) {
                dayRepository.saveAndFlush(day);
            } else {
                log.info("Day exist {}", day.getDate());
            }
        });
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existDayInDayBatch(LocalDate day) {
        return dayBatchRepository.existsByDate(day);
    }

}
