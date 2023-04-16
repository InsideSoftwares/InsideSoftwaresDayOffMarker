package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchUpdateHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteHolidayRequestDTOList implements ItemWriter<List<HolidayRequestDTO>> {

    private final BatchUpdateHolidayService batchUpdateHolidayService;

    @Override
    public void write(Chunk<? extends List<HolidayRequestDTO>> lists)  {
        lists.forEach(holidayRequestDTOS -> holidayRequestDTOS.forEach(batchUpdateHolidayService::updateHoliday));
    }
}
