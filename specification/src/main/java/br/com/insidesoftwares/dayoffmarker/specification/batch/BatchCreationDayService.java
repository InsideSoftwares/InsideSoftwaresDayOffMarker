package br.com.insidesoftwares.dayoffmarker.specification.batch;

import br.com.insidesoftwares.dayoffmarker.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.entity.day.DayBatch;

import java.time.LocalDate;
import java.util.List;

public interface BatchCreationDayService {

    void createDaysBatch(final List<DayBatch> daysBatch);
	void deleteDayBatch(final DayBatch dayBatch);
    void createDays(final List<Day> days);

	boolean existDayInDayBatch(final LocalDate day);

}
