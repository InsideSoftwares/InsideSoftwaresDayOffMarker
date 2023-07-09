package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteHolidayRequestDTOList implements ItemWriter<List<HolidayCreateRequestDTO>> {

    private final BatchHolidayService batchHolidayService;

    @Override
    public void write(Chunk<? extends List<HolidayCreateRequestDTO>> lists)  {
        lists.forEach(holidayRequestDTOS -> holidayRequestDTOS.forEach(batchHolidayService::createHoliday));
    }
}
