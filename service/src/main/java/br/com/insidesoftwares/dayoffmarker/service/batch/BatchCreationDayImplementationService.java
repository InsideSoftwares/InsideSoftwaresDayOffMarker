package br.com.insidesoftwares.dayoffmarker.service.batch;

import br.com.insidesoftwares.dayoffmarker.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.entity.day.DayBatch;
import br.com.insidesoftwares.dayoffmarker.repository.day.DayBatchRepository;
import br.com.insidesoftwares.dayoffmarker.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchCreationDayImplementationService implements BatchCreationDayService {

    private final DayBatchRepository dayBatchRepository;
    private final DayRepository dayRepository;

    @Override
    public void createDaysBatch(List<DayBatch> daysBatch) {
        dayBatchRepository.saveAllAndFlush(daysBatch);
    }

	@Override
	public void deleteDayBatch(DayBatch dayBatch) {
		dayBatchRepository.delete(dayBatch);
	}


	@Override
    public void createDays(List<Day> days) {
        dayRepository.saveAllAndFlush(days);
    }

	@Transactional(readOnly = true)
	@Override
	public boolean existDayInDayBatch(LocalDate day) {
		return dayBatchRepository.existsByDate(day);
	}

}
