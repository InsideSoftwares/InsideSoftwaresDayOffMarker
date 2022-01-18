package br.com.sawcunha.dayoffmarker.batch.write;

import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchUpdateHolidayService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WriteHolidayRequestDTOList implements ItemWriter<List<HolidayRequestDTO>> {

    @Autowired
    private BatchUpdateHolidayService batchUpdateHolidayService;

    @Override
    public void write(List<? extends List<HolidayRequestDTO>> lists)  {
        lists.forEach(holidayRequestDTOS -> holidayRequestDTOS.forEach(holidayRequestDTO -> batchUpdateHolidayService.updateHoliday(holidayRequestDTO)));
    }
}
