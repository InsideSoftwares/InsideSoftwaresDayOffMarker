package br.com.sawcunha.dayoffmarker.batch.process;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestParameterDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessorDayBatch implements ItemProcessor<RequestDTO, List<DayBatch>> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    private Integer getYear(List<RequestParameterDTO> requestParameterDTOS){
        String year = requestParameterDTOS.stream().filter(requestParameterDTO ->
           requestParameterDTO.getTypeParameter().equals(eTypeParameter.YEAR)
        ).findAny().get().getValue();
        return Integer.parseInt(year);
    }

    private Integer getMonth(List<RequestParameterDTO> requestParameterDTOS){
        String month = requestParameterDTOS.stream().filter(requestParameterDTO ->
                requestParameterDTO.getTypeParameter().equals(eTypeParameter.MONTH)
        ).findAny().get().getValue();
        return Integer.parseInt(month);
    }

    private Long getCountry(List<RequestParameterDTO> requestParameterDTOS){
        String country = requestParameterDTOS.stream().filter(requestParameterDTO ->
                requestParameterDTO.getTypeParameter().equals(eTypeParameter.COUNTRY)
        ).findAny().get().getValue();
        return Long.parseLong(country);
    }

    @Override
    public List<DayBatch> process(RequestDTO requestDTO) throws Exception {
        List<DayBatch> dayBatches = new ArrayList<>();
        Country country = batchCreationDayService.findCountry(getCountry(requestDTO.getRequestParameter()));
        int year = getYear(requestDTO.getRequestParameter());
        int month = getMonth(requestDTO.getRequestParameter());

        LocalDate dateBase = LocalDate.of(year,month,1);

        while (dateBase.getYear() == year && dateBase.getMonthValue() == month){
            DayBatch dayBatch = DayBatch.builder()
                    .date(dateBase)
                    .isProcessed(false)
                    .isWeekend(DateUtils.isWeenkend(dateBase))
                    .isHoliday(false)
                    .country(country)
                    .requestID(requestDTO.getId())
                    .build();
            dayBatches.add(dayBatch);
            dateBase = dateBase.plusDays(1);
        }

        return dayBatches;
    }

}
