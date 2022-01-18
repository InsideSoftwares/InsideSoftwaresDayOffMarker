package br.com.sawcunha.dayoffmarker.specification.batch;

import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;

import java.util.List;

public interface BatchCreationDayService {

    List<DayBatch> findAllDayBatchForBatch(final Long jobId);

    Country findCountry(final Long id);

    void createDayBatch(final DayBatch dayBatch);

    void createDay(final Day day);

}
