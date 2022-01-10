package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@StepScope
public class ReaderRequestDTOStatusRunning implements ItemReader<RequestDTO> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    private int nextRequest;
    private List<RequestDTO> requestDTOs;
    private List<UUID> uuid;

    @Value("#{jobParameters['List']}")
    public void setFileName(final String name) {
        uuid = new ArrayList<>();
        Arrays.stream(name.split(",")).forEach(s -> {
            uuid.add(UUID.fromString(s.trim()));
        });
    }

    @Override
    public RequestDTO read() throws Exception {
        if(requestDTOs == null){
            requestDTOs = batchCreationDayService.findAllRequestDTOForBatch(
                    uuid,
                    eStatusRequest.RUNNING
            );
        }
        RequestDTO nextRequestDto = null;

        if (nextRequest < requestDTOs.size()) {
            nextRequestDto = requestDTOs.get(nextRequest++);
        } else {
            nextRequest = 0;
        }

        return nextRequestDto;
    }
}
